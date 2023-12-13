package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.dto.QuestionDto;
import org.example.entity.Question;
import org.example.mapper.QuestionMapper;
import org.example.repository.QuestoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestoinRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    void save() {
        // Arrange
        Question question = new Question();

        // Act
        questionService.save(question);

        // Assert
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void getAll() {
        // Arrange
        int page = 0;
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));

        // Instead of using any(), provide a specific Page object or a matcher
        when(questionRepository.findAll(pageable)).thenReturn(Page.empty());

        // Act
        Page<Question> result = questionService.getAll(page);

        // Assert
        assertEquals(Page.empty(), result);
        verify(questionRepository, times(1)).findAll(pageable);
    }


    @Test
    void count() {
        // Arrange
        when(questionRepository.count()).thenReturn(10L);

        // Act
        long result = questionService.count();

        // Assert
        assertEquals(10L, result);
        verify(questionRepository, times(1)).count();
    }

    @Test
    void getById() {
        // Arrange
        int id = 1;
        Question expectedQuestion = new Question();
        when(questionRepository.findById(id)).thenReturn(Optional.of(expectedQuestion));

        // Act
        Question result = questionService.getById(id);

        // Assert
        assertEquals(expectedQuestion, result);
        verify(questionRepository, times(1)).findById(id);
    }

    @Test
    void edit() {
        // Arrange
        int id = 1;
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(id);

        Question existingQuestion = new Question();
        when(questionRepository.findById(id)).thenReturn(Optional.of(existingQuestion));

        // Act
        questionService.edit(questionDto);

        // Assert
        verify(questionMapper, times(1)).updateEntityFromDto(questionDto, existingQuestion);
        verify(questionRepository, times(1)).save(existingQuestion);
    }

    @Test
    void deleteById() {
        // Arrange
        Integer id = 1;

        // Act
        questionService.deleteById(id);

        // Assert
        verify(questionRepository, times(1)).deleteById(id);
    }
}
