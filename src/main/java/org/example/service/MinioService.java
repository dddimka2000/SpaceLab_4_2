package org.example.service;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
@Log4j2
public class MinioService {

    private final MinioClient minioClient;
    private final String bucketName = "project.4.2";
    private final String imagesBucketName = "images";
    private final String filesBucketName = "files";

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
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

    @SneakyThrows
    public String getUrl(String fileName) {
        log.info("MinioService-getUrl start");

        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket("project.4.2")
                .object("/images/" + fileName)
                .method(Method.GET)
                .build();

        String url = minioClient.getPresignedObjectUrl(args);

        log.info("MinioService-getUrl successfully");
        return url;
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
}