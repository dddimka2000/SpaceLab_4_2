package org.example.util;

import io.minio.errors.*;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Logger;
import org.example.service.MinioService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResponseEntityHelper {
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

        List<String> namesPicturesDelete = pictures.stream()
                .filter(path -> !oldPictures.contains(path))
                .collect(Collectors.toList());
        log.info(namesPicturesDelete);
        namesPicturesDelete.stream().forEach(s-> {
            try {
                minioService.deleteImg(s, imagesBucketName);
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
