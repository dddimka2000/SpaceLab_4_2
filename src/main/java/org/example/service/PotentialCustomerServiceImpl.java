package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.PotentialCustomerDtoForView;
import org.example.entity.PotentialCustomer;
import org.example.entity.property.PropertyHouseObject;
import org.example.entity.property.StatusPotentialCustomer;
import org.example.mapper.PotentialCustomerMapperImpl;
import org.example.repository.PotentialCustomerRepository;
import org.example.specification.HouseObjectSpecification;
import org.example.specification.PotentialCustomerSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class PotentialCustomerServiceImpl {
    private final PotentialCustomerRepository potentialCustomerRepository;
    private final PotentialCustomerMapperImpl potentialCustomerMapper;

    public Page<PotentialCustomerDtoForView> getAll(Integer page, Integer numberOfElement, String search) {
        Pageable pageable = PageRequest.of(page, numberOfElement, Sort.by(Sort.Order.desc("id")));
        Specification<PotentialCustomer> specification = new PotentialCustomerSpecification(search);
        return potentialCustomerMapper.toDtoList(potentialCustomerRepository.findAll(specification, pageable));
    }
    @Transactional
    public void changeStatus(Integer id, StatusPotentialCustomer status) {
        PotentialCustomer potentialCustomer = getById(id);
        potentialCustomer.setStatusPotentialCustomer(status);
        save(potentialCustomer);
    }
    @Transactional
    public void save(PotentialCustomer potentialCustomer){
        potentialCustomerRepository.save(potentialCustomer);
    }
    public PotentialCustomer getById(Integer id){
        return potentialCustomerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("A potential customer with an id = " + id + " was not found")
        );
    }
}