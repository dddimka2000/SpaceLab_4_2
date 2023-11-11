package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repository.PropertyHouseObjectRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HousesServiceImpl {
    private final PropertyHouseObjectRepository propertyHouseObjectRepository;

}
