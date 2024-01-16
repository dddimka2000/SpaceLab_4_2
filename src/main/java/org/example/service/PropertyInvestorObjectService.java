package org.example.service;

import io.minio.errors.*;
import lombok.extern.log4j.Log4j2;
import org.example.dto.PropertyInvestorObjectDTO;
import org.example.entity.Realtor;
import org.example.entity.property.PropertyInvestorObject;
import org.example.entity.property.type.PropertyOrigin;
import org.example.mapper.ObjectInvestorMapper;
import org.example.repository.PropertyInvestorObjectRepository;
import org.example.specification.InvestorObjectSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class PropertyInvestorObjectService {
    private final String imagesBucketName = "images";
    private final String filesBucketName = "files";
    private final
    PropertyInvestorObjectRepository propertyInvestorObjectRepository;
    final
    RealtorServiceImpl realtorService;
    final
    MinioService minioService;
    final
    ObjectBuilderService objectBuilderService;

    public PropertyInvestorObjectService(PropertyInvestorObjectRepository propertyInvestorObjectRepository, MinioService minioService, RealtorServiceImpl realtorService, ObjectBuilderService objectBuilderService) {
        this.propertyInvestorObjectRepository = propertyInvestorObjectRepository;
        this.minioService = minioService;
        this.realtorService = realtorService;
        this.objectBuilderService = objectBuilderService;
    }

    public void save(PropertyInvestorObject entity) {
        log.info("PropertyInvestorObjectService-save start");
        propertyInvestorObjectRepository.save(entity);
        log.info("PropertyInvestorObjectService-save successfully");
    }
    public void saveCreate(PropertyInvestorObject propertyInvestorObject, PropertyInvestorObjectDTO propertyInvestorObjectDTO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("PropertyInvestorObjectService-create start");
        Realtor realtor = realtorService.getByCode(propertyInvestorObjectDTO.getEmployeeCode());
        propertyInvestorObject.setRealtor(realtor);
        propertyInvestorObject.setFiles(minioService.putFileList(propertyInvestorObjectDTO.getFiles()));
        propertyInvestorObject.setPictures(minioService.putImagesList(propertyInvestorObjectDTO.getPictures()));
        propertyInvestorObject.setBuilderObject(objectBuilderService.findById(propertyInvestorObjectDTO.getResidentialComplexId()).get());
        propertyInvestorObject.setPropertyOrigin(PropertyOrigin.INVESTOR);
        save(propertyInvestorObject);
        log.info("PropertyInvestorObjectService-create successfully");
    }
    public void saveEdit(PropertyInvestorObject propertyInvestorObject, PropertyInvestorObjectDTO propertyInvestorObjectDTO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (propertyInvestorObjectDTO.getOldFiles() == null) {
            propertyInvestorObjectDTO.setOldFiles(new ArrayList<>());
        }
        minioService.streamFiles(propertyInvestorObject.getFiles(), propertyInvestorObjectDTO.getOldFiles(), minioService, filesBucketName, propertyInvestorObject.getPictures(), propertyInvestorObjectDTO.getOldPictures(), imagesBucketName, propertyInvestorObjectDTO, propertyInvestorObject);
        log.info(propertyInvestorObjectDTO.getEmployeeCode());
        Realtor realtor = realtorService.getByCode(propertyInvestorObjectDTO.getEmployeeCode());
        propertyInvestorObject.setRealtor(realtor);
        ObjectInvestorMapper.INSTANCE.toOldEntity(propertyInvestorObject, propertyInvestorObjectDTO);
        propertyInvestorObject.setBuilderObject(objectBuilderService.findById(propertyInvestorObjectDTO.getResidentialComplexId()).get());

        List<String> nameFiles = minioService.putFileList(propertyInvestorObjectDTO.getFiles());
        propertyInvestorObjectDTO.getOldFiles().addAll(nameFiles);
        propertyInvestorObject.setFiles(propertyInvestorObjectDTO.getOldFiles());


        List<String> namePictures = minioService.putImagesList(propertyInvestorObjectDTO.getPictures());
        propertyInvestorObjectDTO.getOldPictures().addAll(namePictures);
        propertyInvestorObject.setPictures(propertyInvestorObjectDTO.getOldPictures());
        propertyInvestorObject.setPropertyOrigin(PropertyOrigin.INVESTOR);
        save(propertyInvestorObject);
        log.info("PropertyInvestorObjectService-edit successfully");
    }


    public Optional<PropertyInvestorObject> findByCode(String code) {
        log.info("PropertyInvestorObjectService-findByCode start");
        Optional<PropertyInvestorObject> propertyInvestorObject=propertyInvestorObjectRepository.findByObjectCode(code);
        log.info("PropertyInvestorObjectService-findByCode successfully");
        return propertyInvestorObject;
    }
    public Optional<PropertyInvestorObject> findById(Integer id) {
        log.info("PropertyInvestorObjectService-findById start");
        Optional<PropertyInvestorObject> propertyInvestorObject=propertyInvestorObjectRepository.findById(id);
        log.info("PropertyInvestorObjectService-findById successfully");
        return propertyInvestorObject;
    }
    public void deleteById(Integer entity) {
        log.info("PropertyInvestorObjectService-deleteById start");
        propertyInvestorObjectRepository.deleteById(entity);
        log.info("PropertyInvestorObjectService-deleteById successfully");
    }
    public long count() {
        return propertyInvestorObjectRepository.count();
    }


    public Page<PropertyInvestorObject> forSelect(String name, Pageable pageable) {
        return propertyInvestorObjectRepository.findAll(Specification.where(InvestorObjectSpecification.nameContains(name)), pageable);
    }

    public Page<PropertyInvestorObject> findBuilderObjectsByCriteria(List<String> district,
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
        Specification<PropertyInvestorObject> spec = new InvestorObjectSpecification(
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
        return propertyInvestorObjectRepository.findAll(spec, pageable);
    }


}
