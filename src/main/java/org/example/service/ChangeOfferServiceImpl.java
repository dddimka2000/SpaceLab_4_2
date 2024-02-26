package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.entity.ChangeOffer;
import org.example.repository.ChangeOfferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChangeOfferServiceImpl {

    private final ChangeOfferRepository changeOfferRepository;

    public void save(ChangeOffer changeOffer) {
        log.info("ChangeOfferServiceImpl-save start");
        changeOfferRepository.save(changeOffer);
        log.info("ChangeOfferServiceImpl-save successfully");
    }

    public Page<ChangeOffer> getAll(int page, int size) {
        log.info("ChangeOfferServiceImpl-getAll start");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Page<ChangeOffer> result = changeOfferRepository.findAll(pageable);
        log.info("ChangeOfferServiceImpl-getAll successfully");
        return result;
    }

    public ChangeOffer getById(Integer id) {
        log.info("ChangeOfferServiceImpl-getById start");
        ChangeOffer result = changeOfferRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("An offer with an id = " + id + " was not found"));
        log.info("ChangeOfferServiceImpl-getById successfully");
        return result;
    }

    public long count() {
        log.info("ChangeOfferServiceImpl-count start");
        long result = changeOfferRepository.count();
        log.info("ChangeOfferServiceImpl-count successfully");
        return result;
    }

    public void deleteById(Integer id) {
        log.info("ChangeOfferServiceImpl-deleteById start");
        changeOfferRepository.deleteById(id);
        log.info("ChangeOfferServiceImpl-deleteById successfully");
    }
}

