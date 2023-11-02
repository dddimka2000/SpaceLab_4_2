package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Buyer;
import org.example.repository.BuyerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyerServiceImpl {
    private final BuyerRepository buyerRepository;

    public Buyer getById(int id) {
    return buyerRepository.findById(id).get();
    }
}
