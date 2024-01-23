package org.example.controller;

import io.minio.errors.*;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.entity.UserReview;
import org.example.service.MinioService;
import org.example.service.ReviewServiceImpl;
import org.example.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserServiceImpl userService;

    @Mock
    private ReviewServiceImpl reviewService;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private UserController userController;
    @Test
    void mainPage() {
        ModelAndView result = userController.mainPage();
        assertEquals("users/users_table", result.getViewName());
    }

    @Test
    void userInfo() {
        when(userService.getById(anyInt())).thenReturn(new UserEntity());
        ModelAndView result = userController.userInfo(1);
        assertEquals("users/user_info", result.getViewName());
        assertEquals(new UserEntity(), result.getModel().get("user"));
    }

    @Test
    void add(){
        ModelAndView result = userController.add();
        assertEquals("users/user_add", result.getViewName());
        assertEquals(new UserEntity(), result.getModel().get("userDto"));
    }
    @Test
    void testAdd() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UserDto userDto = new UserDto();
        userDto.setId(2);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        ResponseEntity<String> result = userController.add(userDto, bindingResult);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Користувача успішно збережено", result.getBody());
    }

    @Test
    void edit() {
        ModelAndView result = userController.edit();
        assertEquals("users/user_add", result.getViewName());
    }

    @Test
    void getById() {
        int id = 1;
        UserEntity userEntity = new UserEntity();
        when(userService.getById(id)).thenReturn(userEntity);
        UserEntity result = userController.getById(id);
        assertNotNull(result);
        assertSame(userEntity, result);
    }

    @Test
    void getByUrl() {
        String url = "test-url";
        when(minioService.getUrl(url)).thenReturn("test-url");
        ResponseEntity<String> result = userController.getByUrl(url);
        assertNotNull(result);
        assertEquals("test-url", result.getBody());
    }

    @Test
    void getAll() {
        when(userService.getAll(1, "ROLE_USER", "Doe", "John", "Middle", "123456789", "john.doe@example.com")).thenReturn(mock(Page.class));
        Page<UserEntity> result = userController.getAll(1, "ROLE_USER", "Doe", "John", "Middle", "123456789", "john.doe@example.com");
        assertNotNull(result);
    }

    @Test
    void deleteByIdWhereIdIsEquals() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        when(userService.getAuthUser()).thenReturn(userEntity);
        ResponseEntity<String> result = userController.deleteById(1);
        assertEquals(result.getBody(), "ERROR: Неможна видалити самого себе!!!");
    }
    @Test
    void deleteByIdWhereIdIsNotEquals() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2);
        when(userService.getAuthUser()).thenReturn(userEntity);
        ResponseEntity<String> result = userController.deleteById(1);
        assertEquals(result.getBody(), "ERROR: Це головний адмін його не можна видалити");
    }
    @Test
    void deleteById() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2);
        when(userService.getAuthUser()).thenReturn(userEntity);
        ResponseEntity<String> result = userController.deleteById(3);
        assertEquals(result.getBody(), "Користува успішно видалено");
    }

    @Test
    void uploadFile() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        int id = 1;
        UserEntity userEntity = new UserEntity();
        when(userService.getById(id)).thenReturn(userEntity);
        ResponseEntity<String> result = userController.uploadFile(mock(MultipartFile.class), id);
        assertNotNull(result);
    }

    @Test
    void deleteFile() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        int id = 1;
        String url = "test-url";
        UserEntity userEntity = new UserEntity();
        userEntity.setFiles(new ArrayList<>(Collections.singletonList(url)));
        when(userService.getById(id)).thenReturn(userEntity);
        ResponseEntity<String> result = userController.deleteFile(id, url);
        assertNotNull(result);
    }

    @Test
    void downloadFile() throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        String url = "test-url";
        when(minioService.getPhoto(url, "images")).thenReturn(new byte[]{1, 2, 3});
        byte[] result = userController.downloadFile(url);
        assertNotNull(result);
        assertArrayEquals(new byte[]{1, 2, 3}, result);
    }

    @Test
    void testDeleteFile() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ResponseEntity<String> result = userController.deleteFile(1);
        assertEquals("Відгук видалено успішно", result.getBody());
    }

    @Test
    void saveReview() {
        UserReview review = new UserReview();
        ResponseEntity<String> result = userController.saveReview(review);
        assertNotNull(result);
        assertEquals("Відгук збережено", result.getBody());
    }

    @Test
    void activeMenuItem() {
        Model model = Mockito.mock(Model.class);
        userController.activeMenuItem(model);
        Mockito.verify(model).addAttribute(eq("usersActive"), eq(true));

    }
}