package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.ImagesForObject;
import org.example.repository.ImageForObjectsRepository;
import org.example.entity.property.type.TypeObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*

fixme

divide services by interfaces and implementations

do not return Optional<...> in services, throw not found exception if the value is not present
if you return Optional from services, Controller layer has to check if the value is present or not

*/

@Service
@Log4j2
public class ImagesForObjectService {
    private final
    ImageForObjectsRepository imageForObjectsRepository;

    public ImagesForObjectService(ImageForObjectsRepository imageForObjectsRepository) {
        this.imageForObjectsRepository = imageForObjectsRepository;
    }

    public void save(ImagesForObject entity) {
        log.info("ImagesForObjectService-save start");
        imageForObjectsRepository.save(entity);
        log.info("ImagesForObjectService-save successfully");
    }

    public void deleteById(Integer id) {
        log.info("ImagesForObjectService-deleteById start");
        imageForObjectsRepository.deleteById(id);
        log.info("ImagesForObjectService-deleteById successfully");
    }

    public List<ImagesForObject> findByIdObjectAndTypeObject(Integer id, TypeObject typeObject) {
        log.info("LayoutService-findByIdObjectAndTypeObject start");
        List<ImagesForObject> list=imageForObjectsRepository.findByIdObjectAndTypeObject(id,typeObject);
        log.info("LayoutService-findByIdObjectAndTypeObject successfully");
        return list;
    }
    public Optional<ImagesForObject> findOneByIdObjectAndTypeObject(Integer id, TypeObject typeObject) {
        log.info("LayoutService-findOneByIdObjectAndTypeObject start");
        Optional<ImagesForObject> entity=imageForObjectsRepository.findFirstByIdObjectAndTypeObject(id,typeObject);
        log.info("LayoutService-findOneByIdObjectAndTypeObject successfully");
        return entity;
    }

}
