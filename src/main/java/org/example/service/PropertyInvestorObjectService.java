package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.property.PropertyInvestorObject;
import org.example.repository.PropertyInvestorObjectRepository;
import org.example.specification.InvestorObjectSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PropertyInvestorObjectService {
    private final
    PropertyInvestorObjectRepository propertyInvestorObjectRepository;

    public PropertyInvestorObjectService(PropertyInvestorObjectRepository propertyInvestorObjectRepository) {
        this.propertyInvestorObjectRepository = propertyInvestorObjectRepository;
    }

    public void save(PropertyInvestorObject entity) {
        log.info("PropertyInvestorObjectService-save start");
        propertyInvestorObjectRepository.save(entity);
        log.info("PropertyInvestorObjectService-save successfully");
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
