package org.example.service;

import io.minio.errors.*;
import lombok.extern.log4j.Log4j2;
import org.example.dto.PropertyInvestorObjectDTO;
import org.example.dto.PropertySecondaryObjectDTO;
import org.example.entity.Realtor;
import org.example.entity.property.PropertyInvestorObject;
import org.example.entity.property.PropertySecondaryObject;
import org.example.entity.property.type.PropertyOrigin;
import org.example.mapper.ObjectSecondaryMapper;
import org.example.repository.PropertySecondaryObjectRepository;
import org.example.specification.SecondaryObjectSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PropertySecondaryObjectService {
    private String imagesBucketName = "images";
    private String filesBucketName = "files";
    private final
    PropertySecondaryObjectRepository propertySecondaryObjectRepository;
    final
    RealtorServiceImpl realtorService;
    final
    MinioService minioService;
    final
    ObjectBuilderService objectBuilderService;

    public PropertySecondaryObjectService(PropertySecondaryObjectRepository propertySecondaryObjectRepository, RealtorServiceImpl realtorService, MinioService minioService, ObjectBuilderService objectBuilderService) {
        this.propertySecondaryObjectRepository = propertySecondaryObjectRepository;
        this.realtorService = realtorService;
        this.minioService = minioService;
        this.objectBuilderService = objectBuilderService;
    }
    public void saveEdit(PropertySecondaryObject propertySecondaryObject,  PropertySecondaryObjectDTO propertySecondaryObjectDTO)throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Realtor realtor = realtorService.getById(propertySecondaryObjectDTO.getEmployeeCode());
        minioService.streamFiles(propertySecondaryObject.getFiles(), propertySecondaryObjectDTO.getOldFiles(), log, minioService, filesBucketName, propertySecondaryObject.getPictures(), propertySecondaryObjectDTO.getOldPictures(), imagesBucketName, propertySecondaryObjectDTO, propertySecondaryObject);
        ObjectSecondaryMapper.INSTANCE.toOldEntity(propertySecondaryObject, propertySecondaryObjectDTO);
        propertySecondaryObject.setRealtor(realtor);


        List<String> nameFiles = new ArrayList<>();
        for (MultipartFile multipartFile : propertySecondaryObjectDTO.getFiles()) {
            nameFiles.add(minioService.putFile(multipartFile));
        }
        propertySecondaryObjectDTO.getOldFiles().addAll(nameFiles);
        propertySecondaryObject.setFiles(propertySecondaryObjectDTO.getOldFiles());


        List<String> namePictures = new ArrayList<>();
        for (MultipartFile multipartFile : propertySecondaryObjectDTO.getPictures()) {
            namePictures.add(minioService.putImage(multipartFile));
        }
        propertySecondaryObjectDTO.getOldPictures().addAll(namePictures);
        propertySecondaryObject.setPictures(propertySecondaryObjectDTO.getOldPictures());
        propertySecondaryObject.setBuilderObject(objectBuilderService.findById(propertySecondaryObjectDTO.getResidentialComplexId()).get());
        propertySecondaryObject.setPropertyOrigin(PropertyOrigin.SECONDARY);
        save(propertySecondaryObject);
    }
    public void saveCreate(PropertySecondaryObject propertySecondaryObject,  PropertySecondaryObjectDTO propertySecondaryObjectDTO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("PropertyInvestorObject-create start");
        Realtor realtor = realtorService.getById(propertySecondaryObjectDTO.getEmployeeCode());
        propertySecondaryObject.setRealtor(realtor);
        List<String> nameFiles = new ArrayList<>();
        for (MultipartFile multipartFile : propertySecondaryObjectDTO.getFiles()) {
            nameFiles.add(minioService.putFile(multipartFile));
        }
        propertySecondaryObject.setFiles(nameFiles);
        List<String> namePictures = new ArrayList<>();
        for (MultipartFile multipartFile : propertySecondaryObjectDTO.getPictures()) {
            namePictures.add(minioService.putImage(multipartFile));
        }
        propertySecondaryObject.setBuilderObject(objectBuilderService.findById(propertySecondaryObjectDTO.getResidentialComplexId()).get());

        propertySecondaryObject.setPictures(namePictures);
        propertySecondaryObject.setPropertyOrigin(PropertyOrigin.SECONDARY);
        save(propertySecondaryObject);
        log.info("PropertyInvestorObject-create successfully");
    }

    public void save(PropertySecondaryObject entity) {
        log.info("PropertySecondaryObject-save start");
        propertySecondaryObjectRepository.save(entity);
        log.info("PropertySecondaryObject-save successfully");
    }
    public long count() {
        return propertySecondaryObjectRepository.count();
    }
    public Optional<PropertySecondaryObject> findByCode(String code) {
        log.info("PropertySecondaryObjectService-findByCode start");
        Optional<PropertySecondaryObject> propertySecondaryObject=propertySecondaryObjectRepository.findByObjectCode(code);
        log.info("PropertySecondaryObjectService-findByCode successfully");
        return propertySecondaryObject;
    }
    public Optional<PropertySecondaryObject> findById(Integer id) {
        log.info("PropertySecondaryObject-findById start");
        Optional<PropertySecondaryObject> propertySecondaryObject=propertySecondaryObjectRepository.findById(id);
        log.info("PropertySecondaryObjectService-findById successfully");
        return propertySecondaryObject;
    }
    public void deleteById(Integer entity) {
        log.info("PropertySecondaryObjectService-deleteById start");
        propertySecondaryObjectRepository.deleteById(entity);
        log.info("PropertySecondaryObjectService-deleteById successfully");
    }

    public Page<PropertySecondaryObject> forSelect(String name, Pageable pageable) {
        return propertySecondaryObjectRepository.findAll(Specification.where(SecondaryObjectSpecification.nameContains(name)), pageable);
    }
    public Page<PropertySecondaryObject> findBuilderObjectsByCriteria(List<String> district,
                                                                     Integer numberRooms,
                                                                     Integer minFloor,
                                                                     Integer maxFloor,
                                                                     Integer minPrice,
                                                                     Integer maxPrice,
                                                                     List<String> topozone,
                                                                     List<Integer> residentialComplexId,
                                                                     Integer minNumberFloors,
                                                                     Integer maxNumberFloors,
                                                                     Integer minArea,
                                                                     Integer maxArea,
                                                                     String street,
                                                                     LocalDate lastContactDate,
                                                                     Pageable pageable) {
        Specification<PropertySecondaryObject> spec = new SecondaryObjectSpecification(
                district,
                numberRooms,
                minFloor,
                maxFloor,
                minPrice,
                maxPrice,
                topozone,
                residentialComplexId,
                minNumberFloors,
                maxNumberFloors,
                minArea,
                maxArea,
                street,
                lastContactDate);
        return propertySecondaryObjectRepository.findAll(spec, pageable);
    }


}
