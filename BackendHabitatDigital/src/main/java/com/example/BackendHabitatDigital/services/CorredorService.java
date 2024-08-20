package com.example.BackendHabitatDigital.services;

import com.example.BackendHabitatDigital.entities.CorredorEntity;
import com.example.BackendHabitatDigital.entities.InmuebleEntity;
import com.example.BackendHabitatDigital.entities.RoleEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.repositories.CorredorRepository;
import com.example.BackendHabitatDigital.repositories.InmuebleRepository;
import com.example.BackendHabitatDigital.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CorredorService {
    private final CorredorRepository corredorRepository;
    private final UserRepository userRepository;
    // Just for access to the list of properties that the corredor would be able to take
    private final InmuebleRepository inmuebleRepository;


    @Autowired
    public CorredorService(CorredorRepository corredorRepository, UserRepository userRepository, InmuebleRepository inmuebleRepository) {
        this.corredorRepository = corredorRepository;
        this.userRepository = userRepository;
        this.inmuebleRepository = inmuebleRepository;
    }

    public ResponseEntity<Object> addCorredor(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(email);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("The user doesn't exist", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = optionalUser.get();
        user.setRole(RoleEntity.CORREDOR);

        CorredorEntity corredor = new CorredorEntity();
        corredor.setUser(user);
        corredor.setValoracion(-1);

        corredorRepository.save(corredor);

        return new ResponseEntity<>("Successfully saved", HttpStatus.CREATED);
    }


    public List<CorredorEntity> getAllCorredores() {
        return this.corredorRepository.findAll();
    }

    public Optional<CorredorEntity> getCorredorById(long corredorId) {
        return this.corredorRepository.findById(corredorId);
    }

    // Function to get all properties of the authenticated user (corredor)
    public List<InmuebleEntity> getInmueblesByAuthenticatedCorredor() {
        // Get the current authentication information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the current user ID from the authentication context
        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        // Find the corredor by the user's ID
        CorredorEntity corredor = corredorRepository.findCorredorById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor with user ID " + currentUserId + " not found"));

        // Return the list of properties associated with the corredor
        return corredor.getInmuebles(); // Return the list of inmuebles from the corredor instance
    }
    // Function to get properties without a broker assigned for the authenticated corredor
    public List<InmuebleEntity> getInmueblesWithoutCorredorForAuthenticatedCorredor() {
        // Get the current authentication information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the current user ID from the authentication context
        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        // Verify if the user is a corredor
        CorredorEntity corredor = corredorRepository.findCorredorById(currentUserId)
                .orElseThrow(() -> new SecurityException("User is not authorized or not a corredor"));

        // Return the list of properties without a corredor assigned
        return inmuebleRepository.findInmueblesWithoutCorredor();
    }

}