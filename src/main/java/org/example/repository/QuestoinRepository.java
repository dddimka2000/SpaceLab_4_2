package org.example.repository;

import org.example.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestoinRepository extends JpaRepository<Question, Integer> {
}
