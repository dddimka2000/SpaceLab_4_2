package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.Branch;
import org.example.entity.BuilderObject;
import org.example.repository.BuilderObjectRepository;
import org.example.service.specification.BuilderObjectSpecification;
import org.example.specification.BranchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        Optional<BuilderObject> entity = builderObjectRepository.findById(id);
        log.info("ObjectBuilderService-findById successfully");
        return entity;
    }

    public Page<BuilderObject> findBuilderObjectsByCriteria(String name, String district, String zone, String street, Integer floorQuantity, Integer minPrice, Pageable pageable) {
        Specification<BuilderObject> spec = new BuilderObjectSpecification(name, district, zone, street, floorQuantity, minPrice);
        return builderObjectRepository.findAll(spec, pageable);
    }

    public Optional<BuilderObject> findByName(String name) {
        log.info("ObjectBuilderService-findByName start");
        Optional<BuilderObject> entity = builderObjectRepository.findByName(name);
        log.info("ObjectBuilderService-findByName successfully");
        return entity;
    }

    public void save(BuilderObject entity) {
        log.info("ObjectBuilderService-save start");
        builderObjectRepository.save(entity);
        log.info("ObjectBuilderService-save successfully");
    }

    public void deleteById(Integer id) {
        log.info("ObjectBuilderService-deleteById start: " + id);
        Optional<BuilderObject> builderObject = builderObjectRepository.findById(id);
        if (builderObject.isPresent()) {
            builderObjectRepository.delete(builderObject.get());
            log.info("ObjectBuilderService-deleteById successfully: " + id);
        } else {
            log.error("builderObject empty");
        }
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

    public Page<BuilderObject> forSelect(String name, Pageable pageable) {
        return builderObjectRepository.findAll(Specification.where(BuilderObjectSpecification.nameContains(name)), pageable);
    }
}
