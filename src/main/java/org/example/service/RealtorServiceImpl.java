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
import org.example.service.specification.BranchSpecification;
import org.example.service.specification.RealtorSpecification;
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
        Realtor realtor;
        if (realtorDto.getId() == null) realtor = realtorMapper.toEntity(realtorDto, minioService);
        else {
            realtor = getById(realtorDto.getId());
            realtorMapper.updateEntityFromDto(realtorDto, realtor, minioService);
        }
        for (RealtorContact contact : realtor.getContacts()) {
            contact.setRealtor(realtor);
        }
        realtor.setCreationDate(LocalDate.now());
        save(realtor);
    }

    public Page<Realtor> getAll(int page, String branchId, String code, String fullName, String phone, String email, String birthdate) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));
        return realtorRepository.findAll(Specification.where(RealtorSpecification.branchContains(branchId))
                .and(RealtorSpecification.codeContains(code)).and(RealtorSpecification.phoneContains(phone)).and(RealtorSpecification.birthdateContains(birthdate))
                .and((RealtorSpecification.nameContains(fullName).or(RealtorSpecification.surnameContains(fullName))
                        .or(RealtorSpecification.middlenameContains(fullName)).and(RealtorSpecification.emailContains(email)))), pageable);
    }

    public Realtor getById(int id) {
        return realtorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A realtor with an id = "+id +" was not found"));
    }

    public void deleteById(int id) {
        realtorRepository.deleteById(id);
    }

    public void save(Realtor realtor) {
        realtorRepository.save(realtor);
    }
}
