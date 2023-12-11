package org.example.service;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.BuyerPersonalDataDto;
import org.example.entity.Buyer;
import org.example.entity.BuyerApplication;
import org.example.entity.BuyerApplicationEditLog;
import org.example.entity.BuyerNote;
import org.example.entity.property.PropertyCommercialObject;
import org.example.entity.property._PropertyObject;
import org.example.mapper.BuyerMapper;
import org.example.repository.BuyerApplicationEditLogRepository;
import org.example.repository.BuyerApplicationRepository;
import org.example.repository.BuyerNoteRepository;
import org.example.repository.BuyerRepository;
import org.example.service.specification.BuyerForObjectSpecification;
import org.example.service.specification.BuyerSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BuyerServiceImpl {

    private final BuyerRepository buyerRepository;
    private final BuyerApplicationRepository buyerApplicationRepository;
    private final BuyerNoteRepository buyerNoteRepository;
    private final BuyerMapper buyerMapper;
    private final MinioService minioService;
    private final BuyerApplicationEditLogRepository buyerApplicationEditLogRepository;
    private final CommercialServiceImpl commercialService;
    private final HousesServiceImpl housesService;
    private final PropertyInvestorObjectService propertyInvestorObjectService;
    private final PropertySecondaryObjectService propertySecondaryObjectService;

    public Integer addPersonalData(BuyerPersonalDataDto buyerDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("BuyerServiceImpl-addPersonalData start");
        Buyer buyer;
        if(buyerDto.getId()== null){
            buyer = buyerMapper.toEntity(buyerDto, minioService);
        }else {
            buyer = getById(buyerDto.getId());
            buyerMapper.updateEntityFromDto(buyerDto, buyer, minioService);
        }
        Integer result = save(buyer).getId();
        log.info("BuyerServiceImpl-addPersonalData successfully");
        return result;
    }

    public Buyer getById(int id) {
        log.info("BuyerServiceImpl-getById start");
        Buyer result = buyerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A buyer with an id = "+id +" was not found"));
        log.info("BuyerServiceImpl-getById successfully");
        return result;
    }

    @Transactional
    public Buyer save(Buyer buyer){
        log.info("BuyerServiceImpl-save start");
        Buyer result = buyerRepository.save(buyer);
        log.info("BuyerServiceImpl-save successfully");
        return result;
    }

    @Transactional
    public void addApplication(BuyerApplication buyerApplication) {
        log.info("BuyerServiceImpl-addApplication start");
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
        log.info("BuyerServiceImpl-addApplication successfully");
    }

    public void addNote(BuyerNote buyerNote) {
        log.info("BuyerServiceImpl-addNote start");
        buyerNoteRepository.save(buyerNote);
        log.info("BuyerServiceImpl-addNote successfully");
    }

    public void deleteNote(Integer id) {
        log.info("BuyerServiceImpl-deleteNote start");
        buyerNoteRepository.deleteById(id);
        log.info("BuyerServiceImpl-deleteNote successfully");
    }

    public Page<Buyer> getAll(Integer page, String branch, String realtor, String name, String phone, String email, String price) {
        log.info("BuyerServiceImpl-getAll start");
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));
        Page<Buyer> result = buyerRepository.findAll(Specification.where(BuyerSpecification.branchContains(branch).and(BuyerSpecification.realtorContains(realtor))
                .and(BuyerSpecification.middleNameContains(name).or(BuyerSpecification.nameContains(name)).or(BuyerSpecification.surnameContains(name)))
                .and(BuyerSpecification.phoneContains(phone)).and(BuyerSpecification.emailContains(email)).and(BuyerSpecification.priceGreaterThanOrEqual(price))),pageable);
        log.info("BuyerServiceImpl-getAll successfully");
        return result;
    }

    public void deleteById(Integer id) {
        log.info("BuyerServiceImpl-deleteById start");
        buyerRepository.deleteById(id);
        log.info("BuyerServiceImpl-deleteById successfully");
    }

    public List<Buyer> filterForObject(Integer id, String type) {
        log.info("BuyerServiceImpl-filterForObject start");
        List<BuyerApplication> applications = new ArrayList<>();
        switch (type){
            case "COMMERCIAL":
                applications = buyerApplicationRepository.findAll(new BuyerForObjectSpecification(commercialService.getById(id)));
                break;
            case "HOUSE":
                applications = buyerApplicationRepository.findAll(new BuyerForObjectSpecification(housesService.getById(id)));
                break;
            case "INVESTOR":
                applications = buyerApplicationRepository.findAll(new BuyerForObjectSpecification(propertyInvestorObjectService.findById(id).get()));
                break;
            case "SECONDARY":
                applications = buyerApplicationRepository.findAll(new BuyerForObjectSpecification(propertySecondaryObjectService.findById(id).get()));
        }
        List<Buyer> result = applications.stream()
                .map(BuyerApplication::getBuyer)
                .collect(Collectors.toList());
        log.info("BuyerServiceImpl-filterForObject successfully");
        return result;
    }
}
