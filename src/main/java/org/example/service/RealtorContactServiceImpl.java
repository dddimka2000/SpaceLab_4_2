package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.entity.Realtor;
import org.example.repository.RealtorContactRepository;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class RealtorContactServiceImpl {
    private final RealtorContactRepository realtorContactRepository;
    public void deleteById(int id){
        log.info("RealtorContactServiceImpl-deleteById start");
        realtorContactRepository.deleteById(id);
        log.info("RealtorContactServiceImpl-deleteById successfully");
    }
}
