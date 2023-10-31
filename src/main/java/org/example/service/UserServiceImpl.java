package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    public UserEntity getById(int id){
        return userRepository.findById(id).get();
    }
}
