package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Function to create a User
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @GetMapping("/get/{userEmail}")
    public UserEntity getUser(@PathVariable String userEmail) {
        return userService.getUserByUsername(userEmail);
    }

    @PutMapping("/update")
    public UserEntity updateUser(@RequestBody UserEntity user) {
        return userService.updateUser(user);
    }
}

