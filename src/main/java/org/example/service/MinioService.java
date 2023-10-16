package org.example.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {
    String bucketName="project.4.2";
    String filePathImg = "path/to/local/file.jpg";


    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void putImg(MultipartFile multipartFile) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        File tempFile = File.createTempFile("tempfile", multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(multipartFile.getOriginalFilename())
                .stream(new FileInputStream(tempFile), tempFile.length(), -1)
                .contentType(multipartFile.getContentType())
                .build());

        tempFile.delete();
    }
    public void deleteImg(String objectName) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }

}
