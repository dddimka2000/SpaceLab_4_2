package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.BuilderObject;
import org.example.repository.BuilderObjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ObjectBuilderService {
    private final
    BuilderObjectRepository builderObjectRepository;

    public ObjectBuilderService(BuilderObjectRepository builderObjectRepository) {
        this.builderObjectRepository = builderObjectRepository;
    }

    public Optional<BuilderObject> findById(Integer id) {
        log.info("ObjectBuilderService-findById start");
        Optional<BuilderObject> entity=builderObjectRepository.findById(id);
        log.info("ObjectBuilderService-findById successfully");
        return entity;
    }

    public void save(BuilderObject entity) {
        log.info("ObjectBuilderService-save start");
        builderObjectRepository.save(entity);
        log.info("ObjectBuilderService-save successfully");
    }

    public void deleteById(Integer id) {
        log.info("ObjectBuilderService-deleteById start");
        builderObjectRepository.deleteById(id);
        log.info("ObjectBuilderService-deleteById successfully");
    }

    public Page<BuilderObject> findBuilderObjectsPage(Integer pageNumber, Integer pageSize) {
        log.info("ObjectBuilderService-findAllQuestionPages start");
        Page<BuilderObject> page = null;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        page = builderObjectRepository.findAll(pageable);
        log.info("ObjectBuilderService with " + pageNumber + " have been found");
        log.info("ObjectBuilderService-findAllQuestionPages successfully");
        return page;
    }
}
