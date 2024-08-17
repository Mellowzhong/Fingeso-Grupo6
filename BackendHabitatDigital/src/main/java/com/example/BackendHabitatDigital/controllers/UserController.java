package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.entities.User;
import com.example.BackendHabitatDigital.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Crear usuario
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Encontrar usuario segun su correo
    @GetMapping("/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
}
