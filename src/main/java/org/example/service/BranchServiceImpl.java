package org.example.service;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.mapper.BranchMapper;
import org.example.repository.BranchRepository;
import org.example.specification.BranchSpecification;
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
@Log4j2
public class BranchServiceImpl {
    private final MinioService minioService;
    private final BranchMapper branchMapper;
    private final BranchRepository branchRepository;
    public void add(BranchDto branchDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("BranchServiceImpl-add start");
        Branch branch = new Branch();
        if(branchDto.getId()==null) {
            branch = branchMapper.toEntity(branchDto, minioService);
        }
        else {
            branch = getById(branchDto.getId());
            branchMapper.updateEntityFromDto(branchDto, branch, minioService);
        }
        save(branch);
        log.info("BranchServiceImpl-add successfully");
    }
    public long count() {
        log.info("BranchServiceImpl-count start");
        long res = branchRepository.count();
        log.info("BranchServiceImpl-count successfully");
        return res;
    }
    public Branch getById(int id){
        log.info("BranchServiceImpl-getById start");
        Branch res = branchRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A branch with an id = "+id +", was not found"));
        log.info("BranchServiceImpl-getById successfully");
        return res;
    }
    public Page<Branch> getAll(int page, String code, String name, String address) {
        log.info("BranchServiceImpl-getAll start");
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));
        Page<Branch> res = branchRepository.findAll(Specification.where(BranchSpecification.codeContains(code))
                .and(BranchSpecification.nameContains(name))
                .and(BranchSpecification.addressContains(address)), pageable);
        log.info("BranchServiceImpl-getAll successfully");
        return res;
    }
    public Page<Branch> forSelect(String name, Pageable pageable) {
        log.info("BranchServiceImpl-forSelect start");
        Page<Branch> res = branchRepository.findAll(Specification.where(BranchSpecification.nameContains(name)), pageable);
        log.info("BranchServiceImpl-forSelect successfully");
        return res;
    }
    public void deleteById(int id){
        log.info("BranchServiceImpl-deleteById start");
        branchRepository.deleteById(id);
        log.info("BranchServiceImpl-deleteById successfully");
    }
    @Transactional
    public void save(Branch branch){
        log.info("BranchServiceImpl-save start");
        branchRepository.save(branch);
        log.info("BranchServiceImpl-save successfully");
    }
    public int countByCode(int code){
        log.info("BranchServiceImpl-countByCode start");
        int res = branchRepository.countByCode(code);
        log.info("BranchServiceImpl-countByCode successfully");
        return res;
    }
    public Branch getByCode(int id){
        log.info("BranchServiceImpl-getByCode start");
        Branch res = branchRepository.findByCode(id).orElseThrow(() -> new EntityNotFoundException("A branch with an id = "+id +", was not found"));
        log.info("BranchServiceImpl-getByCode successfully");
        return res;
    }

    public boolean existsByPhone(String phone) {
        return branchRepository.existsByTelephone(phone);
    }
}
