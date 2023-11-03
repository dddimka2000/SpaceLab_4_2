package org.example.repository;

import org.example.entity.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//fixme @Repository is not needed
@Repository
public interface PageRepository extends JpaRepository<PageEntity, Integer> {
}
