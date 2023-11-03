package org.example.repository;

import org.example.entity.ExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//fixme @Repository is not needed
@Repository
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRates, Integer> {

    //fixme this is not needed
    @Override
    <S extends ExchangeRates> S save(S entity);

    Optional<ExchangeRates> findByName(String name);

}
