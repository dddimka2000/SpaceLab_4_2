package org.example.service;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.apache.commons.compress.utils.IOUtils;
import org.example.service.MinioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MinIoServiceImplTest {

    @Mock
    private MinioClient minioClient;

    @InjectMocks
    private MinioService minioService;

    @Test
    void putMultipartFile_Success() throws Exception {
        // Arrange
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
        String directory = "test";
        String nameFile = "test.txt";

        // Act
        minioService.putMultipartFile(multipartFile, directory, nameFile);

        // Assert
        verify(minioClient).putObject(any());
    }

    @Test
    void putMultipartFile_IOException() throws Exception {
        // Arrange
        MockMultipartFile multipartFile = Mockito.mock(MockMultipartFile.class);
        String directory = "test";
        String nameFile = "test.txt";

        // Mocking IOException when transferring the file
        doThrow(IOException.class).when(multipartFile).transferTo(any(File.class));

        // Act & Assert
        assertThrows(IOException.class, () -> minioService.putMultipartFile(multipartFile, directory, nameFile));
    }

    @Test
    void deleteImg_Success() throws Exception {
        // Arrange
        String objectName = "test.txt";
        String directory = "test";

        // Act
        minioService.deleteImg(objectName, directory);

        // Assert
        verify(minioClient).removeObject(any());
    }


    // Add more tests for other methods in a similar manner
    @Test
    void putMultipartFile() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Arrange
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
        String directory = "test";
        String nameFile = "test.txt";

        // Act
        minioService.putMultipartFile(multipartFile, directory, nameFile);

        // Assert
        verify(minioClient).putObject(any(PutObjectArgs.class));
    }

    @Test
    void putImage() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Arrange
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "fake image content".getBytes());

        // Act
        String result = minioService.putImage(image);

        // Assert
        assertNotNull(result);
        assertTrue(result.endsWith(".jpg"));
        verify(minioClient).putObject(any(PutObjectArgs.class));
    }
}