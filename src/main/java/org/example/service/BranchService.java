package org.example.service;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.mapper.BranchMapper;
import org.example.repository.BranchRepository;
import org.example.service.specification.BranchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/*

fixme

divide services by interfaces and implementations

do not return Optional<...> in services, throw not found exception if the value is not present
if you return Optional from services, Controller layer has to check if the value is present or not

add logs
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class BranchService {

    private final MinioService minioService;
    private final BranchMapper branchMapper;
    private final BranchRepository branchRepository;

    // fixme process all of these exceptions with try-catch here, do not throw them to controller
    public void add(BranchDto branchDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Branch branch = new Branch();
        if(branchDto.getId()==null) branch = branchMapper.toEntity(branchDto, minioService);
        else {

            //fixme check if present
            branch = branchRepository.findById(branchDto.getId()).get();
            branchMapper.updateEntityFromDto(branchDto, branch, minioService);
        }

        branchRepository.save(branch);
    }

    // fixme check if present
    public Branch getById(int id){
        return branchRepository.findById(id).get();
    }
    public Page<Branch> getAll(int page, String code, String name, String address) {


        /*
        alternative approach
        sort order can be included in specification (ex. in BranchSpecification) , but it's not necessary
         */
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("id")));

        return branchRepository.findAll(Specification.where(BranchSpecification.codeContains(code))
                .and(BranchSpecification.nameContains(name))
                .and(BranchSpecification.addressContains(address)), pageable);
    }
    public Page<Branch> forSelect(String name, Pageable pageable) {
        return branchRepository.findAll(Specification.where(BranchSpecification.nameContains(name)), pageable);
    }
    public void deleteById(int id){
        branchRepository.deleteById(id);
    }
}
