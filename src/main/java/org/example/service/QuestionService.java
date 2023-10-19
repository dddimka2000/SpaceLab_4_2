package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.Question;
import org.example.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QuestionService {
    private final
    QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> findQuestionsPage(Integer pageNumber, Integer pageSize) {
        log.info("QuestionService-findAllQuestionPages start");
        Page<Question> page = null;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        page = questionRepository.findAll(pageable);
        log.info("QuestionService with " + pageNumber + " have been found");
        log.info("QuestionService-findAllQuestionPages successfully");
        return page;
    }

}
