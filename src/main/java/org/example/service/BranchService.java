package org.example.service;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.mapper.BranchMapper;
import org.example.repository.BranchRepository;
import org.example.validator.BranchValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class BranchService {
    private final MinioService minioService;
    private final BranchValidator branchValidator;
    private final BranchMapper branchMapper;
    private final BranchRepository branchRepository;
    public void add(BranchDto branchDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Branch branch = branchMapper.toEntity(branchDto);
        branch.setImgPath(minioService.putImage(branchDto.getImgPath()));
        branchRepository.save(branch);
    }
    public Branch getById(long id){
        return branchRepository.findById(id).get();
    }
    public Page<Branch> getAll(int page) {
        Pageable pageable = PageRequest.of(--page, 10, Sort.by(Sort.Order.asc("id")));
        return branchRepository.findAll(pageable);
    }
    public Page<Branch> getAll(int page, String name, String address) {
        Pageable pageable = PageRequest.of(--page, 10, Sort.by(Sort.Order.asc("id")));
        return branchRepository.findAllByNameContainingAndAddressContaining(name, address, pageable);
    }
    public void deleteById(long id){
        branchRepository.deleteById(id);
    }
}
