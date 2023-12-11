package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.entity.UserReview;
import org.example.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl {

    private final ReviewRepository reviewRepository;

    public void deleteById(int id) {
        log.info("ReviewServiceImpl-deleteById start");
        reviewRepository.deleteById(id);
        log.info("ReviewServiceImpl-deleteById successfully");
    }

    public void save(UserReview review) {
        log.info("ReviewServiceImpl-save start");
        reviewRepository.save(review);
        log.info("ReviewServiceImpl-save successfully");
    }
}

