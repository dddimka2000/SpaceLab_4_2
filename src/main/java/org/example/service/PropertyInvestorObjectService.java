package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.dto.ObjectBuilderDtoSearch;
import org.example.entity.BuilderObject;
import org.example.entity.property.PropertyInvestorObject;
import org.example.repository.PropertyInvestorObjectRepository;
import org.example.service.specification.BuilderObjectSpecification;
import org.example.service.specification.InvestorObjectSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

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
