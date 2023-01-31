package com.mycompany.recipe.service;

import com.mycompany.recipe.exception.UserNotFoundException;
import com.mycompany.recipe.repository.UserRepository;
import com.mycompany.recipe.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public void userExistsOrThrow(UUID userId) {
        if (!userRepository.existsById(userId))
            throw new UserNotFoundException(userId.toString());
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public List<UserEntity> listAllUsers() {
        return userRepository.findAll();
    }

    @SneakyThrows
    public UserEntity findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
    }
}
