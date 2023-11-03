package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
public class QuestionServiceImpl {
    private final QuestoinRepository questionRepository;
    private final QuestionMapper questionMapper;
    public void save(Question question) {
        questionRepository.save(question);
    }

    public Page<Question> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));
        return questionRepository.findAll(pageable);
    }

    public Question getById(int id) {
        return questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A question with an id = "+id +" was not found"));
    }

    public void edit(QuestionDto questionDto) {
        Question question = getById(questionDto.getId());
        questionMapper.updateEntityFromDto(questionDto, question);
        save(question);
    }

    public void deleteById(Integer id) {
        questionRepository.deleteById(id);
    }
}
