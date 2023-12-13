package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    final
    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User hasn't been found!");
        } else {
            log.info(username + " has been found");
        }

        return new UserDetailsImpl(userEntity.get());
    }
    public void save(UserEntity user) {
        userRepository.save(user);
    }
    public Optional<UserEntity> findByEmail(String user) {
        return userRepository.findByEmail(user);
    }
}

