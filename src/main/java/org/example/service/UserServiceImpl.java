package org.example.service;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.example.service.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MinioService minioService;
    public UserEntity getById(int id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A user with an id = "+id +" was not found"));
    }

    public void add(UserDto userDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UserEntity user;
        if(userDto.getId() == null) user = userMapper.toEntity(userDto);
        else {
            user = getById(userDto.getId());
            userMapper.updateEntityFromDto(userDto, user, minioService);
        }
        save(user);
    }
    public void save(UserEntity user){
        userRepository.save(user);
    }

    public Page<UserEntity> getAll(int page, String role, String surname, String name, String middleName, String phone, String email) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("id")));
        return userRepository.findAll(Specification.where(UserSpecification.roleContains(role)).and(UserSpecification.surnameContains(surname)
                .and(UserSpecification.nameContains(name)).and(UserSpecification.middleNameContains(middleName)).and(UserSpecification.phoneContains(phone))
                .and(UserSpecification.emailContains(email))), pageable);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
