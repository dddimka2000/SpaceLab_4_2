package org.example.service;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.example.dto.BuyerPersonalDataDto;
import org.example.entity.Buyer;
import org.example.entity.BuyerApplication;
import org.example.entity.BuyerApplicationEditLog;
import org.example.entity.BuyerNote;
import org.example.mapper.BuyerMapper;
import org.example.repository.BuyerApplicationEditLogRepository;
import org.example.repository.BuyerApplicationRepository;
import org.example.repository.BuyerNoteRepository;
import org.example.repository.BuyerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BuyerServiceImpl {
    private final BuyerRepository buyerRepository;
    private final BuyerApplicationRepository buyerApplicationRepository;
    private final BuyerNoteRepository buyerNoteRepository;
    private final BuyerMapper buyerMapper;
    private final MinioService minioService;
    private final BuyerApplicationEditLogRepository buyerApplicationEditLogRepository;
    public Integer addPersonalData(BuyerPersonalDataDto buyerDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Buyer buyer;
        if(buyerDto.getId()== null){
            buyer = buyerMapper.toEntity(buyerDto, minioService);
        }else {
            buyer = getById(buyerDto.getId());
            buyerMapper.updateEntityFromDto(buyerDto, buyer, minioService);
        }
        return save(buyer).getId();
    }
    public Buyer getById(int id) {
        return buyerRepository.findById(id).get();
    }
    @Transactional
    public Buyer save(Buyer buyer){
        return buyerRepository.save(buyer);
    }

    @Transactional
    public void addApplication(BuyerApplication buyerApplication) {
        if(buyerApplication.getId()!=null) {
            BuyerApplicationEditLog history = new BuyerApplicationEditLog();
            history.setDate(LocalDate.now());
            history.setDescription(buyerApplication.toString());
            history.setApplication(buyerApplication);
            buyerApplicationEditLogRepository.save(history);
        }
        buyerApplicationRepository.save(buyerApplication);
        Buyer buyer = buyerApplication.getBuyer();
        buyer.setApplication(buyerApplication);
        buyerRepository.save(buyer);
    }

    public void addNote(BuyerNote buyerNote) {
        buyerNoteRepository.save(buyerNote);
    }
    public void deleteNote(Integer id) {
        buyerNoteRepository.deleteById(id);
    }

    public Page<Buyer> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));
        return buyerRepository.findAll(pageable);
    }
}
