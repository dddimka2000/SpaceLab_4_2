package org.example.service;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.example.specification.UserSpecification;
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
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MinioService minioService;

    public UserEntity getById(int id) {
        log.info("UserServiceImpl-getById start");
        UserEntity result = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A user with an id = " + id + " was not found"));
        log.info("UserServiceImpl-getById successfully");
        return result;
    }

    public void add(UserDto userDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("UserServiceImpl-add start");

        UserEntity user;
        if (userDto.getId() == null) {
            user = userMapper.toEntity(userDto);
        } else {
            user = getById(userDto.getId());
            userMapper.updateEntityFromDto(userDto, user, minioService);
        }

        save(user);

        log.info("UserServiceImpl-add successfully");
    }

    @Transactional
    public void save(UserEntity user) {
        log.info("UserServiceImpl-save start");
        userRepository.save(user);
        log.info("UserServiceImpl-save successfully");
    }

    public Page<UserEntity> getAll(int page, String role, String surname, String name, String middleName, String phone, String email) {
        log.info("UserServiceImpl-getAll start");

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("id")));
        Page<UserEntity> result = userRepository.findAll(Specification.where(UserSpecification.roleContains(role))
                .and(UserSpecification.surnameContains(surname).and(UserSpecification.nameContains(name))
                        .and(UserSpecification.middleNameContains(middleName)).and(UserSpecification.phoneContains(phone))
                        .and(UserSpecification.emailContains(email))), pageable);

        log.info("UserServiceImpl-getAll successfully");
        return result;
    }

    public void deleteById(int id) {
        log.info("UserServiceImpl-deleteById start");
        userRepository.deleteById(id);
        log.info("UserServiceImpl-deleteById successfully");
    }
    public UserEntity getAuthUser(){
        return userRepository.findById(1).get();
    }
}