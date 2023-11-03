package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.PageEntity;
import org.example.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*

fixme

divide services by interfaces and implementations

do not return Optional<...> in services, throw not found exception if the value is not present
if you return Optional from services, Controller layer has to check if the value is present or not

add logs
 */

@Service
@Log4j2
public class PageService {
    public final
    PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public Optional<PageEntity> findById(Integer id) {
        log.info("PageEntityService-findById start");
        Optional<PageEntity> PageEntity=pageRepository.findById(id);
        log.info("PageEntityService-findById successfully");
        return PageEntity;
    }
    public void save(PageEntity entity) {
        pageRepository.save(entity);
    }
    public void deleteById(Integer id) {
        log.info("PageEntityService-deleteById start");
        pageRepository.deleteById(id);
        log.info("PageEntityService-deleteById successfully");
    }
    public List<PageEntity> findAll() {
        log.info("PageEntityService-findAll start");
        List<PageEntity> list=pageRepository.findAll();
        log.info("PageEntityService-findAll successfully");
        return list;
    }
}
