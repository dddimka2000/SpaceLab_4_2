package org.example.service;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.example.dto.RealtorDto;
import org.example.entity.Realtor;
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

@Service
@RequiredArgsConstructor
public class RealtorServiceImpl {
    private final RealtorRepository realtorRepository;
    private final RealtorMapper realtorMapper;
    private final MinioService minioService;
    @Transactional
    public void add(RealtorDto realtorDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Realtor realtor;
        if(realtorDto.getId() == null) realtor = realtorMapper.toEntity(realtorDto, minioService);
        else{
            realtor = realtorRepository.findById(realtorDto.getId()).get();
            realtorMapper.updateEntityFromDto(realtorDto, realtor, minioService);
        }
        realtorRepository.save(realtor);
    }

    public Page<Realtor> getAll(int page, String branchId, String code, String fullName, String phone, String email, String birthdate) {
        Pageable pageable = PageRequest.of(page, 3, Sort.by(Sort.Order.desc("id")));
        return realtorRepository.findAll(Specification.where(RealtorSpecification.branchIdContains(branchId))
                .and(RealtorSpecification.codeContains(code)).and(RealtorSpecification.middlenameContains(fullName))
                .or(RealtorSpecification.nameContains(fullName).or(RealtorSpecification.surnameContains(fullName))
                        .and(RealtorSpecification.emailContains(email))), pageable);
    }

    public Realtor getById(int id) {
        return realtorRepository.findById(id).get();
    }

    public void deleteById(int id) {
        realtorRepository.deleteById(id);
    }
}
