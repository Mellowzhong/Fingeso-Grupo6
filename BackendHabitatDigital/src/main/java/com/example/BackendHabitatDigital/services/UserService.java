package com.example.BackendHabitatDigital.services;

import com.example.BackendHabitatDigital.entities.OwnerEntity;
import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.entities.RoleEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.jwt.JwtAuthenticationFilter;
import com.example.BackendHabitatDigital.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    private final PasswordEncoder passwordEncoder;
    
    // Crear un usuario
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    // Encontrar un usuario segun su email
//    public Optional<UserEntity> findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

    // Retorna todos los usuarios en la base de datos
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserByProfileId(Long id) {
        return userRepository.findByProfileId(id);
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean userExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserEntity updateUser(UserEntity user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }

        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + user.getId() + " does not exist."));

        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }

        ProfileEntity newProfile = user.getProfile();
        if (newProfile != null) {
            ProfileEntity existingProfile = existingUser.getProfile();
            if(user.getProfile() != null){
                existingProfile.setFirstname(newProfile.getFirstname());
                existingProfile.setLastname(newProfile.getLastname());
                existingProfile.setContact(newProfile.getContact());
                existingProfile.setDescription(newProfile.getDescription());
                existingProfile.setPicture(newProfile.getPicture());
            }
        }

        return userRepository.save(existingUser);
    }

    public UserEntity updatePassword(UserEntity user) {
        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + user.getId() + " does not exist."));

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity updateUserRole(UserEntity user) throws Exception {
        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + user.getId() + " does not exist."));

        if (user.getRole() != null) {
            if (!Objects.equals(user.getRole().toString(), "ADMIN")) {
                RoleEntity role = RoleEntity.fromString(user.getRole().toString());
                existingUser.setRole(role);
                return userRepository.save(existingUser);
            }
        }

        throw new Exception("Try a different role.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteUser(Long id) throws Exception {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
