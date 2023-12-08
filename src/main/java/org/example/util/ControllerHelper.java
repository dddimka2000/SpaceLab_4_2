package org.example.util;

import io.minio.errors.*;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Logger;
import org.example.entity.BuilderObject;
import org.example.entity.ImagesForObject;
import org.example.entity.property.type.TypeObject;
import org.example.service.ImagesForObjectService;
import org.example.service.MinioService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ControllerHelper {
    @NotNull
    public static <T> ResponseEntity<Object> getResponseEntity(boolean empty, List<String> files, MinioService minioService, String filesBucketName, Optional<T> objectBuilder) {
        List<byte[]> fileDataList;
        if (empty) {
            ResponseEntity.badRequest();
        }
        fileDataList = files.stream().map(s -> {
            try {
                return minioService.getPhoto(s, filesBucketName);
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException |
                     IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        List<String> base64FileList = fileDataList.stream()
                .map(Base64.getEncoder()::encodeToString)
                .toList();

        return ResponseEntity.ok().body(base64FileList);
    }
    public static  <T,D> void streamFiles(List<String> files, List<String> oldFiles, Logger log, MinioService minioService, String filesBucketName, List<String> pictures, List<String> oldPictures, String imagesBucketName, @ModelAttribute @Valid T propertySecondaryObjectDTO, D propertySecondaryObject) {
        minIoSend(files, oldFiles, log, minioService, filesBucketName);
        minIoSend(pictures, oldPictures, log, minioService, imagesBucketName);
    }

    public static void minIoSend(List<String> files, List<String> oldFiles, Logger log, MinioService minioService, String filesBucketName) {
        List<String> namesFilesDelete = files.stream()
                .filter(path -> !oldFiles.contains(path))
                .collect(Collectors.toList());
        log.info(namesFilesDelete);
        namesFilesDelete.stream().forEach(s-> {
            try {
                minioService.deleteImg(s, filesBucketName);
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException |
                     IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public static <T> void saveFilesInMinIO(BuilderObject builderObject, List<MultipartFile> files, @ModelAttribute @Valid T objectBuilderDto, String imagesBucketName, MinioService minioService, ImagesForObjectService imagesForObjectService) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String uuidFile;
        String resultFilename;
        for (MultipartFile file : files) {
            ImagesForObject imagesForObject = new ImagesForObject();
            imagesForObject.setIdObject(builderObject.getId());
            imagesForObject.setTypeObject(TypeObject.BY_BUILDER);

            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file.getOriginalFilename();
            minioService.putMultipartFile(file, imagesBucketName, resultFilename);
            imagesForObject.setPath(resultFilename);

            imagesForObjectService.save(imagesForObject);
        }
    }
}
