package org.example.repository;

import org.example.entity.BuyerNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerNoteRepository extends JpaRepository<BuyerNote, Integer> {
}
