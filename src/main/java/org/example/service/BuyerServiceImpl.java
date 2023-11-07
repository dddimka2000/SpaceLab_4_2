package org.example.service;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.example.dto.BuyerPersonalDataDto;
import org.example.entity.Buyer;
import org.example.entity.BuyerApplication;
import org.example.mapper.BuyerMapper;
import org.example.repository.BuyerApplicationRepository;
import org.example.repository.BuyerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class BuyerServiceImpl {
    private final BuyerRepository buyerRepository;
    private final BuyerApplicationRepository buyerApplicationRepository;
    private final BuyerMapper buyerMapper;
    private final MinioService minioService;
    public void addPersonalData(BuyerPersonalDataDto buyerDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Buyer buyer;
        if(buyerDto.getId()== null){
            buyer = buyerMapper.toEntity(buyerDto, minioService);
        }else {
            buyer = getById(buyerDto.getId());
            buyerMapper.updateEntityFromDto(buyerDto, buyer, minioService);
        }
        save(buyer);
    }
    public Buyer getById(int id) {
        return buyerRepository.findById(id).get();
    }
    @Transactional
    public void save(Buyer buyer){
        buyerRepository.save(buyer);
    }

    public void addApplication(BuyerApplication buyerApplication) {
        buyerApplicationRepository.save(buyerApplication);
    }
}
