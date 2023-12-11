package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.entity.RealtorFeedBack;
import org.example.repository.RealtorFeedBackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RealtorFeedBackServiceImpl {

    private final RealtorFeedBackRepository realtorFeedBackRepository;

    public void deleteById(int id) {
        log.info("RealtorFeedBackServiceImpl-deleteById start");
        realtorFeedBackRepository.deleteById(id);
        log.info("RealtorFeedBackServiceImpl-deleteById successfully");
    }

    @Transactional
    public void save(RealtorFeedBack realtorFeedBack) {
        log.info("RealtorFeedBackServiceImpl-save start");
        realtorFeedBackRepository.save(realtorFeedBack);
        log.info("RealtorFeedBackServiceImpl-save successfully");
    }

    public List<RealtorFeedBack> getAll() {
        log.info("RealtorFeedBackServiceImpl-getAll start");
        List<RealtorFeedBack> result = realtorFeedBackRepository.findAll();
        log.info("RealtorFeedBackServiceImpl-getAll successfully");
        return result;
    }
}