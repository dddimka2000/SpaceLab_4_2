package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.RealtorFeedBack;
import org.example.repository.RealtorFeedBackRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealtorFeedBackServiceImpl {
    private final RealtorFeedBackRepository realtorFeedBackRepository;
    public void deleteById(int id){
        realtorFeedBackRepository.deleteById(id);
    }
    public void save(RealtorFeedBack realtorFeedBack){
        realtorFeedBackRepository.save(realtorFeedBack);
    }
}
