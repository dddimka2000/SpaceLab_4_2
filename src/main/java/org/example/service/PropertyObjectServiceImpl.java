package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repository.PropertyObjectRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyObjectServiceImpl {
    private final PropertyObjectRepository propertyObjectRepository;
    public Boolean checkCode(String code) {
        return propertyObjectRepository.existsByObjectCode(code);
    }

    public boolean existsByPhone(String phone) {
        return propertyObjectRepository.existsByOwnerPhone(phone);
    }
}