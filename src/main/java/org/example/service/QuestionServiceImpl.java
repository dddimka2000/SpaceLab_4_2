package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.QuestionDto;
import org.example.entity.Question;
import org.example.mapper.QuestionMapper;
import org.example.repository.QuestoinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuestionServiceImpl {

    private final QuestoinRepository questionRepository;
    private final QuestionMapper questionMapper;

    public void save(Question question) {
        log.info("QuestionServiceImpl-save start");
        questionRepository.save(question);
        log.info("QuestionServiceImpl-save successfully");
    }

    public Page<Question> getAll(int page) {
        log.info("QuestionServiceImpl-getAll start");

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));
        Page<Question> result = questionRepository.findAll(pageable);

        log.info("QuestionServiceImpl-getAll successfully");
        return result;
    }

    public long count() {
        log.info("QuestionServiceImpl-count start");
        long result = questionRepository.count();
        log.info("QuestionServiceImpl-count successfully");
        return result;
    }

    public Question getById(int id) {
        log.info("QuestionServiceImpl-getById start");
        Question result = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("A question with an id = " + id + " was not found"));
        log.info("QuestionServiceImpl-getById successfully");
        return result;
    }

    public void edit(QuestionDto questionDto) {
        log.info("QuestionServiceImpl-edit start");

        Question question = getById(questionDto.getId());
        questionMapper.updateEntityFromDto(questionDto, question);
        save(question);

        log.info("QuestionServiceImpl-edit successfully");
    }

    public void deleteById(Integer id) {
        log.info("QuestionServiceImpl-deleteById start");
        questionRepository.deleteById(id);
        log.info("QuestionServiceImpl-deleteById successfully");
    }
}