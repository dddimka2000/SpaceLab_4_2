package org.example.service;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
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
public class MinioService {
    String bucketName="project.4.2";

    private final MinioClient minioClient;
    String imagesBucketName = "images";
    String filesBucketName = "files";

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void putMultipartFile(MultipartFile multipartFile, String directory,String nameFile) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
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
    }

    public void deleteImg(String objectName, String directory) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        String prefix = "/" + directory + "/";
        String objectNameToDelete = prefix + objectName;

        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectNameToDelete)
                .build());
    }



    public byte[] getPhoto(String objectName, String directory) throws ErrorResponseException, InsufficientDataException
            , InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException
            , XmlParserException, IOException {
        String prefix = "/" + directory + "/";
        String objectNameToRetrieve = prefix + objectName;

        InputStream objectStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectNameToRetrieve)
                        .build());

        byte[] photoBytes = IOUtils.toByteArray(objectStream);

        objectStream.close();

        return photoBytes;
    }

    public String getFileInString(String objectName, String directory) throws ErrorResponseException, InsufficientDataException
            , InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException
            , XmlParserException, IOException {
        String prefix = "/" + directory + "/";
        String objectNameToRetrieve = prefix + objectName;

        InputStream objectStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectNameToRetrieve)
                        .build());

        byte[] photoBytes = IOUtils.toByteArray(objectStream);

        objectStream.close();
        return Base64.getEncoder().encodeToString(photoBytes);
    }
    public String putImage(MultipartFile image) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1);
        String name = UUID.randomUUID()+"."+image.getOriginalFilename();
        putMultipartFile(image, imagesBucketName, name);
        return name;
    }
    public String putFile(MultipartFile image) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1);
        String name = UUID.randomUUID()+"."+extension;
        putMultipartFile(image, filesBucketName, name);
        return name;
    }
    @SneakyThrows
    public String getUrl(String fileName) {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket("project.4.2")
                .object("/images/"+fileName)
                .method(Method.GET)
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }
    public long getObjectSize(String objectName) throws ErrorResponseException, InsufficientDataException,
            InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException,
            XmlParserException, IOException {
        String prefix = "/images/";
        String objectNameToRetrieve = prefix + objectName;

        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectNameToRetrieve)
                        .build());

        return stat.size();
    }
}
