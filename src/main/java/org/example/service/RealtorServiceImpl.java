package org.example.service;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.RealtorDto;
import org.example.entity.Realtor;
import org.example.entity.RealtorContact;
import org.example.mapper.RealtorMapper;
import org.example.repository.RealtorRepository;
import org.example.specification.BranchSpecification;
import org.example.specification.RealtorSpecification;
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

@Service
@RequiredArgsConstructor
@Log4j2
public class RealtorServiceImpl {

    private final RealtorRepository realtorRepository;
    private final RealtorMapper realtorMapper;
    private final MinioService minioService;

    @Transactional
    public void add(RealtorDto realtorDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("RealtorServiceImpl-add start");

        Realtor realtor;
        if (realtorDto.getId() == null) {
            realtor = realtorMapper.toEntity(realtorDto, minioService);
        }
        else
        {
            realtor = getById(realtorDto.getId());
            realtorMapper.updateEntityFromDto(realtorDto, realtor, minioService);
        }

        for (RealtorContact contact : realtor.getContacts())
        {
            contact.setRealtor(realtor);
        }

        realtor.setCreationDate(LocalDate.now());
        save(realtor);

        log.info("RealtorServiceImpl-add successfully");
    }

    public Page<Realtor> getAll(int page, String branchId, String code, String fullName, String phone, String email, String birthdate) {
        log.info("RealtorServiceImpl-getAll start");

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));
        Page<Realtor> result = realtorRepository.findAll(Specification.where(RealtorSpecification.branchContains(branchId))
                .and(RealtorSpecification.codeContains(code)).and(RealtorSpecification.phoneContains(phone)).and(RealtorSpecification.birthdateContains(birthdate))
                .and((RealtorSpecification.nameContains(fullName).or(RealtorSpecification.surnameContains(fullName))
                        .or(RealtorSpecification.middlenameContains(fullName)).and(RealtorSpecification.emailContains(email)))), pageable);

        log.info("RealtorServiceImpl-getAll successfully");
        return result;
    }

    public Realtor getById(int id) {
        log.info("RealtorServiceImpl-getById start");
        Realtor result = realtorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A realtor with an id = " + id + " was not found"));
        log.info("RealtorServiceImpl-getById successfully");
        return result;
    }

    public void deleteById(int id) {
        log.info("RealtorServiceImpl-deleteById start");
        realtorRepository.deleteById(id);
        log.info("RealtorServiceImpl-deleteById successfully");
    }

    @Transactional
    public void save(Realtor realtor) {
        log.info("RealtorServiceImpl-save start");
        realtorRepository.save(realtor);
        log.info("RealtorServiceImpl-save successfully");
    }

    public Page<Realtor> forSelect(String name, Pageable pageable) {
        log.info("RealtorServiceImpl-forSelect start");
        Page<Realtor> result = realtorRepository.findAll(Specification.where(BranchSpecification.nameContains(name)), pageable);
        log.info("RealtorServiceImpl-forSelect successfully");
        return result;
    }

    public int countByCode(int code) {
        log.info("RealtorServiceImpl-countByCode start");
        int result = realtorRepository.countByCode(code);
        log.info("RealtorServiceImpl-countByCode successfully");
        return result;
    }

    public Realtor getByCode(Integer staffCode) {
        log.info("RealtorServiceImpl-getByCode start");
        Realtor result = realtorRepository.findByCode(staffCode);
        log.info("RealtorServiceImpl-getByCode successfully");
        return result;
    }
}