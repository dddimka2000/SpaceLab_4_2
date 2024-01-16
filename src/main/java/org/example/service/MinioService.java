package org.example.service;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.Logger;
import org.example.entity.BuilderObject;
import org.example.entity.ImagesForObject;
import org.example.entity.property.type.TypeObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class MinioService {

    private final MinioClient minioClient;
    private final
    ImagesForObjectService imagesForObjectService;
    private final String bucketName = "project.4.2";
    private final String imagesBucketName = "images";
    private final String filesBucketName = "files";


    public MinioService(MinioClient minioClient, ImagesForObjectService imagesForObjectService) {
        this.minioClient = minioClient;
        this.imagesForObjectService = imagesForObjectService;
    }

    public void putMultipartFile(MultipartFile multipartFile, String directory, String nameFile) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("MinioService-putMultipartFile start");

        String prefix = "/" + directory + "/";
        String objectName = prefix + nameFile;

        File tempFile = File.createTempFile("tempfile", nameFile);
        multipartFile.transferTo(tempFile);

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(new FileInputStream(tempFile), tempFile.length(), -1)
                .contentType(multipartFile.getContentType())
                .build());

        tempFile.delete();

        log.info("MinioService-putMultipartFile successfully");
    }

    public void deleteImg(String objectName, String directory) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        log.info("MinioService-deleteImg start");

        String prefix = "/" + directory + "/";
        String objectNameToDelete = prefix + objectName;

        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectNameToDelete)
                .build());

        log.info("MinioService-deleteImg successfully");
    }

    public byte[] getPhoto(String objectName, String directory) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        log.info("MinioService-getPhoto start");

        String prefix = "/" + directory + "/";
        String objectNameToRetrieve = prefix + objectName;

        InputStream objectStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectNameToRetrieve)
                        .build());

        byte[] photoBytes = IOUtils.toByteArray(objectStream);

        objectStream.close();

        log.info("MinioService-getPhoto successfully");
        return photoBytes;
    }

    public String getFileInString(String objectName, String directory) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        log.info("MinioService-getFileInString start");

        String prefix = "/" + directory + "/";
        String objectNameToRetrieve = prefix + objectName;

        InputStream objectStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectNameToRetrieve)
                        .build());

        byte[] fileBytes = IOUtils.toByteArray(objectStream);

        objectStream.close();

        log.info("MinioService-getFileInString successfully");
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    public String putImage(MultipartFile image) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("MinioService-putImage start");

        String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1);
        String name = UUID.randomUUID() + "." + extension;
        putMultipartFile(image, imagesBucketName, name);

        log.info("MinioService-putImage successfully");
        return name;
    }

    public String putFile(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("MinioService-putFile start");

        String name = UUID.randomUUID() + "." + file.getOriginalFilename();
        putMultipartFile(file, filesBucketName, name);

        log.info("MinioService-putFile successfully");
        return name;
    }
    public List<String> putImagesList(List<MultipartFile> files) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("MinioService-putFiles start");
        List<String> names=new ArrayList<>();
        for (MultipartFile file : files) {
            String name = UUID.randomUUID() + "." + file.getOriginalFilename();
            names.add(name);
            putMultipartFile(file, imagesBucketName, name);
        }
        log.info("MinioService-putFiles successfully");
        return names;
    }

    public List<String> putFileList(List<MultipartFile> files) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("MinioService-putFiles start");
        List<String> names=new ArrayList<>();
        for (MultipartFile file : files) {
            String name = UUID.randomUUID() + "." + file.getOriginalFilename();
            names.add(name);
            putMultipartFile(file, filesBucketName, name);
        }
        log.info("MinioService-putFiles successfully");
        return names;
    }

    @SneakyThrows
    public String getUrl(String fileName) {
        return "data:image/jpeg;base64, " + Base64.getEncoder().encodeToString(getPhoto(fileName, "images"));
    }
    public long getObjectSize(String objectName) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        log.info("MinioService-getObjectSize start");

        String prefix = "/images/";
        String objectNameToRetrieve = prefix + objectName;

        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectNameToRetrieve)
                        .build());

        log.info("MinioService-getObjectSize successfully");
        return stat.size();
    }
    public static void minIoSend(List<String> files, List<String> oldFiles, MinioService minioService, String filesBucketName) {
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
    public <T> void saveFilesInMinIO(BuilderObject builderObject, List<MultipartFile> files, @ModelAttribute @Valid T objectBuilderDto, String imagesBucketName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String uuidFile;
        String resultFilename;
        for (MultipartFile file : files) {
            ImagesForObject imagesForObject = new ImagesForObject();
            imagesForObject.setIdObject(builderObject.getId());
            imagesForObject.setTypeObject(TypeObject.BY_BUILDER);

            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file.getOriginalFilename();
            putMultipartFile(file, imagesBucketName, resultFilename);
            imagesForObject.setPath(resultFilename);
            imagesForObjectService.save(imagesForObject);
        }
    }
    public static  <T,D> void streamFiles(List<String> files, List<String> oldFiles, MinioService minioService, String filesBucketName, List<String> pictures, List<String> oldPictures, String imagesBucketName, @ModelAttribute @Valid T propertySecondaryObjectDTO, D propertySecondaryObject) {
        minIoSend(files, oldFiles, minioService, filesBucketName);
        minIoSend(pictures, oldPictures, minioService, imagesBucketName);
    }
    @NotNull
    public <T> ResponseEntity<Object> getResponseEntity(boolean empty, List<String> files, String filesBucketName) {
        List<byte[]> fileDataList;
        if (empty) {
            ResponseEntity.badRequest();
        }
        fileDataList = files.stream().map(s -> {
            try {
                return getPhoto(s, filesBucketName);
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
}