package org.example.service;

import org.example.repository.PropertyInvestorObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyInvestorObjectService {
    private final
    PropertyInvestorObjectRepository propertyInvestorObjectRepository;

    public PropertyInvestorObjectService(PropertyInvestorObjectRepository propertyInvestorObjectRepository) {
        this.propertyInvestorObjectRepository = propertyInvestorObjectRepository;
    }

}
