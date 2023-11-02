package org.example.service;

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
    private final QuestoinRepository questoinRepository;
    private final QuestionMapper questionMapper;
    public void save(Question question) {
        questoinRepository.save(question);
    }

    public Page<Question> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));
        return questoinRepository.findAll(pageable);
    }

    public Question getById(int id) {
        return questoinRepository.findById(id).get();
    }

    public void edit(QuestionDto questionDto) {
        Question question = getById(questionDto.getId());
        questionMapper.updateEntityFromDto(questionDto, question);
        save(question);
    }
}
