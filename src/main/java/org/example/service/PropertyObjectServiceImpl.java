package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.property._PropertyObject;
import org.example.repository.PropertyObjectRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyObjectServiceImpl {
    private final PropertyObjectRepository propertyObjectRepository;
    public Boolean existsByCode(String code) {
        return propertyObjectRepository.existsByObjectCode(code);
    }
    public boolean existsByPhone(String phone) {
        return propertyObjectRepository.existsByOwnerPhone(phone);
    }
    public boolean existsByName(String name) {
        return propertyObjectRepository.existsByOwnerName(name);
    }
    public _PropertyObject getByPhone(String phone) {
        return propertyObjectRepository.findByOwnerPhone(phone).orElseThrow(()->new EntityNotFoundException("A object with phone = " + phone + " was not found"));
    }

    public _PropertyObject getById(Integer id) {
        return propertyObjectRepository.findById(id).orElseThrow(()->new EntityNotFoundException("A object with an id = " + id + " was not found"));
    }
}