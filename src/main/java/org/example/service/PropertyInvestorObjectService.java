package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.BuilderObject;
import org.example.entity.property.PropertyInvestorObject;
import org.example.repository.PropertyInvestorObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
