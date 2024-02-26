package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.repository.PotentialCustomerRepository;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PotentialCustomerServiceImpl {
    private final PotentialCustomerRepository potentialCustomerRepository;

}