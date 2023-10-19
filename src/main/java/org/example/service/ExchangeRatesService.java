package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.ExchangeRates;
import org.example.entity.Question;
import org.example.repository.ExchangeRatesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ExchangeRatesService {
    private final
    ExchangeRatesRepository exchangeRatesRepository;

    public ExchangeRatesService(ExchangeRatesRepository exchangeRatesRepository) {
        this.exchangeRatesRepository = exchangeRatesRepository;
    }

    public Optional<ExchangeRates> findByName(String name) {
        log.info("ExchangeRatesService-findByName start");
        Optional<ExchangeRates> exchange_rates=exchangeRatesRepository.findByName(name);
        log.info("ExchangeRatesService-findByName successfully");
        return exchange_rates;
    }

    public void save(ExchangeRates entity) {
        log.info("ExchangeRatesService-save start");
        exchangeRatesRepository.save(entity);
        log.info("ExchangeRatesService-save successfully");
    }


}
