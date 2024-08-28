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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorredorService {
    private final CorredorRepository corredorRepository;
    private final UserRepository userRepository;
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

    public List<InmuebleEntity> getInmueblesByAuthenticatedCorredor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        CorredorEntity corredor = corredorRepository.findCorredorById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor with user ID " + currentUserId + " not found"));

        return corredor.getInmuebles();
    }

    public List<InmuebleEntity> getInmueblesWithoutAcceptedCorredor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();
        CorredorEntity corredor = corredorRepository.findCorredorByUserId(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor with user ID " + currentUserId + " not found"));

        return inmuebleRepository.findByCorredorIsNullAndInmueblesIn(corredor.getInmuebles());
    }

    public ResponseEntity<String> acceptProperty(Long inmuebleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        CorredorEntity corredor = corredorRepository.findCorredorByUserId(currentUserId)
                .orElseThrow(() -> new SecurityException("User is not authorized or not a corredor"));

        InmuebleEntity inmueble = inmuebleRepository.findById(inmuebleId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found with ID: " + inmuebleId));

        if (corredor.getInmuebles().contains(inmueble) && inmueble.getCorredor() == null) {
            inmueble.setCorredor(corredor);
            inmuebleRepository.save(inmueble);
            return ResponseEntity.ok("Property accepted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to accept this property or it has already been accepted.");
        }
    }
}
