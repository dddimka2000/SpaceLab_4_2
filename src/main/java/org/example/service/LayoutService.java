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
    private final
    LayoutRepository layoutRepository;

    public LayoutService(LayoutRepository layoutRepository) {
        this.layoutRepository = layoutRepository;
    }

    public List<Layout> findByBuilderObject(BuilderObject entity) {
        List<Layout> list = layoutRepository.findByBuilderObject(entity);
        return list;
    }

    public void save(Layout entity) {
        layoutRepository.save(entity);
    }

    public void deleteById(Integer id) {
        layoutRepository.deleteById(id);
    }

    public List<Layout> findLayoutsPage(BuilderObject builderObject) {
        List<Layout> list = layoutRepository.findByBuilderObject(builderObject);
        return list;
    }

    @Transactional
    public void deleteAllByBuilderObject(BuilderObject builderObject) {
        layoutRepository.deleteAllByBuilderObject(builderObject);
        log.info("LayoutService deleteAllByBuilderObject");
    }

}
