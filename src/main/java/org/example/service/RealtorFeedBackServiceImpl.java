package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.RealtorFeedBack;
import org.example.repository.RealtorFeedBackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RealtorFeedBackServiceImpl {
    private final RealtorFeedBackRepository realtorFeedBackRepository;
    public void deleteById(int id){
        realtorFeedBackRepository.deleteById(id);
    }
    @Transactional
    public void save(RealtorFeedBack realtorFeedBack){
        realtorFeedBackRepository.save(realtorFeedBack);
    }
}
