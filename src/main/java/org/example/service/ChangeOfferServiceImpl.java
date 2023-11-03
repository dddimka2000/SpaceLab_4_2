package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.ChangeOffer;
import org.example.repository.ChangeOfferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeOfferServiceImpl {
    private final ChangeOfferRepository changeOfferRepository;

    public void save(ChangeOffer changeOffer) {
        changeOfferRepository.save(changeOffer);
    }

    public Page<ChangeOffer> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));
        return changeOfferRepository.findAll(pageable);
    }

    public ChangeOffer getById(Integer id) {
        return changeOfferRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A offer with an id = "+id +", was not found"));
    }

    public void deleteById(Integer id) {
        changeOfferRepository.deleteById(id);
    }
}
