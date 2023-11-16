package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.property.PropertyInvestorObject;
import org.example.entity.property.PropertySecondaryObject;
import org.example.entity.property.PropertySecondaryObject;
import org.example.entity.property.type.PropertySecondaryType;
import org.example.repository.PropertySecondaryObjectRepository;
import org.example.repository.PropertySecondaryObjectRepository;
import org.example.service.specification.InvestorObjectSpecification;
import org.example.service.specification.SecondaryObjectSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PropertySecondaryObjectService {
    String imagesBucketName = "images";
    String filesBucketName = "files";
    private final
    PropertySecondaryObjectRepository propertySecondaryObjectRepository;

    public PropertySecondaryObjectService(PropertySecondaryObjectRepository propertySecondaryObjectRepository) {
        this.propertySecondaryObjectRepository = propertySecondaryObjectRepository;
    }

    public void save(PropertySecondaryObject entity) {
        log.info("PropertySecondaryObject-save start");
        propertySecondaryObjectRepository.save(entity);
        log.info("PropertySecondaryObject-save successfully");
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
