package com.mycompany.recipe.controller;

import com.mycompany.recipe.controller.model.User;
import com.mycompany.recipe.converter.UserConverter;
import com.mycompany.recipe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Validated
public class UserController {

    private UserService userService;

    @PostMapping("/api/v1/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(UserConverter.toApiModel(userService.createUser(UserConverter.toEntity(user))));
    }

    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(UserConverter.toApiModel(userService.findById(userId)));
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.listAllUsers()
                .stream()
                .map(UserConverter::toApiModel)
                .collect(Collectors.toList()));
    }
}
