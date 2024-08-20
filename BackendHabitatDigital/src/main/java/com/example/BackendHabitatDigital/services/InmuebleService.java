package com.example.BackendHabitatDigital.services;


import com.example.BackendHabitatDigital.entities.InmuebleEntity;
import com.example.BackendHabitatDigital.entities.OwnerEntity;
import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.entities.RoleEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.repositories.InmuebleRepository;
import com.example.BackendHabitatDigital.repositories.OwnerRepository;
import com.example.BackendHabitatDigital.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InmuebleService {

    private final InmuebleRepository inmuebleRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public InmuebleService(InmuebleRepository inmuebleRepository, UserRepository userRepository, OwnerRepository ownerRepository) {
        this.inmuebleRepository = inmuebleRepository;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
    }

    // Function to add a new product
    public ResponseEntity<InmuebleEntity> addProduct(InmuebleEntity inmueble, String userEmail) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(userEmail);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // Verify if user has already an associated OwnerEntity
            OwnerEntity owner = ownerRepository.findByUser(user)
                    .orElseGet(() -> {
                        OwnerEntity newOwner = new OwnerEntity();
                        newOwner.setUser(user);
                        return ownerRepository.save(newOwner);
                    });

            inmueble.setOwner(owner);
            inmuebleRepository.save(inmueble);
            return new ResponseEntity<>(inmueble, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Function to get all products
    public List<InmuebleEntity> getAllInmuebles() {
        return this.inmuebleRepository.findAll();
    }

    public Optional<InmuebleEntity> getInmuebleById(long productId) {
        return this.inmuebleRepository.findById(productId);
    }

    // Function to delete a product
    public ResponseEntity<Object> deleteInmueble(long inmuebleId) {
        this.inmuebleRepository.deleteById(inmuebleId);
        return new ResponseEntity<>("Se eliminó con éxito", HttpStatus.OK);
    }

    public List<InmuebleEntity> findAllInmueblesByOwner(long userId) {
        Optional<OwnerEntity> owner = ownerRepository.findById(userId);
        Optional<List<InmuebleEntity>> inmuebles = this.inmuebleRepository.findAllByOwner(owner.get());
        return inmuebles.get();

    }

    public ProfileEntity getOwnerProfile(long inmuebleid){
        Optional<InmuebleEntity> inmuebleO= inmuebleRepository.findById(inmuebleid);
        InmuebleEntity inmueble = inmuebleO.get();
        return inmueble.getOwner().getUser().getProfile();
    }
    // Function to get all properties with null in the corredor field
    public List<InmuebleEntity> findInmueblesWithoutCorredor() {
        return inmuebleRepository.findByCorredorIsNull();
    }
}
