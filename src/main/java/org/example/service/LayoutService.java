package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.BuilderObject;
import org.example.entity.Layout;
import org.example.repository.LayoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class LayoutService {

    private final LayoutRepository layoutRepository;

    public LayoutService(LayoutRepository layoutRepository) {
        this.layoutRepository = layoutRepository;
    }

    public List<Layout> findByBuilderObject(BuilderObject entity) {
        log.info("LayoutService-findByBuilderObject start");
        List<Layout> list = layoutRepository.findByBuilderObject(entity);
        log.info("LayoutService-findByBuilderObject successfully");
        return list;
    }

    public void save(Layout entity) {
        log.info("LayoutService-save start");
        layoutRepository.save(entity);
        log.info("LayoutService-save successfully");
    }

    public void deleteById(Integer id) {
        log.info("LayoutService-deleteById start");
        layoutRepository.deleteById(id);
        log.info("LayoutService-deleteById successfully");
    }

    public List<Layout> findLayoutsPage(BuilderObject builderObject) {
        log.info("LayoutService-findLayoutsPage start");
        List<Layout> list = layoutRepository.findByBuilderObject(builderObject);
        log.info("LayoutService-findLayoutsPage successfully");
        return list;
    }

    @Transactional
    public void deleteAllByBuilderObject(BuilderObject builderObject) {
        log.info("LayoutService-deleteAllByBuilderObject start");
        layoutRepository.deleteAllByBuilderObject(builderObject);
        log.info("LayoutService-deleteAllByBuilderObject successfully");
    }
}

