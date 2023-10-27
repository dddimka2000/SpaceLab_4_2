package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Realtor;
import org.example.repository.RealtorContactRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealtorContactServiceImpl {
    private final RealtorContactRepository realtorContactRepository;
    private final RealtorServiceImpl realtorService;
    public void deleteById(int id){
        realtorContactRepository.deleteById(id);
    }
}
