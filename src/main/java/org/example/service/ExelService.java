package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.ExchangeRates;
import org.example.entity.StreetExelEntity;
import org.example.repository.AddressExelRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ExelService {
    private final AddressExelRepository addressRepository;

    public ExelService(AddressExelRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    public Boolean checkStreetExist(String name) {
        log.info("StreetExelEntity-findByName start");
        Boolean exchange_rates=addressRepository.existsByStreetName(name);
        log.info("StreetExelEntity-findByName successfully");
        return exchange_rates;
    }

}
