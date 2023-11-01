package org.example.repository;

import org.example.entity.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<UserReview, Integer> {
}
