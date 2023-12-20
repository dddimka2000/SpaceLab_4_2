package org.example.controller;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import org.example.dto.QuestionDto;
import org.example.entity.Question;
import org.example.service.BuyerServiceImpl;
import org.example.service.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {
    @Mock
    private QuestionServiceImpl questionService;
    @Mock
    private BuyerServiceImpl buyerService;
    @InjectMocks
    private QuestionController questionController;

    @Test
    void index() {
        ModelAndView modelAndView = questionController.index();
        assertEquals("questions/question_table", modelAndView.getViewName());
    }

    @Test
    void getAll() {
        int page = 1;
        Page<Question> questionPage = mock(Page.class);
        when(questionService.getAll(page)).thenReturn(questionPage);
        Page<Question> result = questionController.getAll(page);
        assertEquals(questionPage, result);
        verify(questionService, times(1)).getAll(page);
    }

    @Test
    void getById() {
        int id = 1;
        Question question = mock(Question.class);
        when(questionService.getById(id)).thenReturn(question);
        Question result = questionController.getById(id);
        assertEquals(question, result);
        verify(questionService, times(1)).getById(id);
    }

    @Test
    void edit() {
        QuestionDto questionDto = mock(QuestionDto.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        ResponseEntity<String> result = questionController.edit(questionDto, bindingResult);
        assertEquals(ResponseEntity.ok().body("Відповідь відредагована успішно"), result);
        verify(questionService, times(1)).edit(questionDto);
    }
    @Test
    void editWithInvalidDate() {
        QuestionDto questionDto = mock(QuestionDto.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("field", "error message")));
        ResponseEntity<String> result = questionController.edit(questionDto, bindingResult);
        verify(questionService, never()).edit(any());
    }

    @Test
    void deleteById() {
        Integer id = 1;
        ResponseEntity<String> result = questionController.deleteById(id);
        assertEquals(ResponseEntity.ok().body("Елемент видалено успішно"), result);
        verify(questionService, times(1)).deleteById(id);
    }

    @Test
    void activeMenuItem() {
        Model model = mock(Model.class);
        questionController.activeMenuItem(model);
        verify(model, times(1)).addAttribute("questionsActive", true);
    }
}