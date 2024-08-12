package com.example.BackendHabitatDigital.services;

import com.example.BackendHabitatDigital.entities.User;
import com.example.BackendHabitatDigital.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Crear un usuario
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Encontrar un usuario segun su email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Retorna todos los usuarios en la base de datos
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
