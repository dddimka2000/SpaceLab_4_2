package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.UserReview;
import org.example.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl {
    private final ReviewRepository reviewRepository;
    public void deleteById(int id){
        reviewRepository.deleteById(id);
    }
    public void save(UserReview review){
        reviewRepository.save(review);
    }
}
