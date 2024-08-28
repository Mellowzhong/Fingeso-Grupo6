package com.example.BackendHabitatDigital.services;

import com.example.BackendHabitatDigital.entities.*;
import com.example.BackendHabitatDigital.repositories.CorredorRepository;
import com.example.BackendHabitatDigital.repositories.InmuebleRepository;
import com.example.BackendHabitatDigital.repositories.OwnerRepository;
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
public class InmuebleService {

    private final InmuebleRepository inmuebleRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final CorredorRepository corredorRepository;

    @Autowired
    public InmuebleService(InmuebleRepository inmuebleRepository, UserRepository userRepository, OwnerRepository ownerRepository, CorredorRepository corredorRepository) {
        this.inmuebleRepository = inmuebleRepository;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.corredorRepository = corredorRepository;
    }

    public ResponseEntity<InmuebleEntity> addProperty(InmuebleEntity inmueble, String userEmail) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(userEmail);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // Cambiar el rol del usuario a OWNER si aún no lo es
            if (!user.getRole().equals(RoleEntity.OWNER)) {
                user.setRole(RoleEntity.OWNER);
                userRepository.save(user); // Guardar el cambio de rol en la base de datos
            }

            // Crear u obtener la entidad OwnerEntity asociada al usuario
            OwnerEntity owner = ownerRepository.findByUser(user)
                    .orElseGet(() -> {
                        OwnerEntity newOwner = new OwnerEntity();
                        newOwner.setUser(user);
                        return ownerRepository.save(newOwner);
                    });

            inmueble.setOwner(owner); // Asignar el propietario al inmueble
            inmuebleRepository.save(inmueble); // Guardar el inmueble en la base de datos
            return new ResponseEntity<>(inmueble, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    public List<InmuebleEntity> getAllInmuebles() {
        return this.inmuebleRepository.findAll();
    }

    public Optional<InmuebleEntity> getInmuebleById(long inmuebleId) {
        return this.inmuebleRepository.findById(inmuebleId);
    }

    public ResponseEntity<Object> deleteInmueble(long inmuebleId) {
        this.inmuebleRepository.deleteById(inmuebleId);
        return new ResponseEntity<>("Se eliminó con éxito", HttpStatus.OK);
    }

    public List<InmuebleEntity> findAllInmueblesByOwner(long userId) {
        Optional<OwnerEntity> owner = ownerRepository.findById(userId);
        return owner.map(value -> this.inmuebleRepository.findAllByOwner(value).orElse(List.of())).orElse(List.of());
    }

    public ProfileEntity getOwnerProfile(long inmuebleId) {
        Optional<InmuebleEntity> inmuebleO = inmuebleRepository.findById(inmuebleId);
        return inmuebleO.map(inmueble -> inmueble.getOwner().getUser().getProfile()).orElse(null);
    }

    public List<InmuebleEntity> findInmueblesWithoutCorredor() {
        return inmuebleRepository.findByCorredorIsNull();
    }

    public ResponseEntity<String> assignCorredorToInmueble(Long inmuebleId, Long corredorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentOwnerUsername = authentication.getName(); // Esto es el email

        OwnerEntity owner = ownerRepository.findByUserEmail(currentOwnerUsername)
                .orElseThrow(() -> new SecurityException("User is not authorized or not an owner"));

        InmuebleEntity inmueble = inmuebleRepository.findById(inmuebleId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found with ID: " + inmuebleId));

        if (!inmueble.getOwner().equals(owner)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to modify this property.");
        }

        CorredorEntity corredor = corredorRepository.findById(corredorId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor not found with ID: " + corredorId));

        // Agregar inmueble a la lista del corredor en memoria
        if (!corredor.getInmuebles().contains(inmueble)) {
            corredor.getInmuebles().add(inmueble);
        }

        // Solo guarda cambios del corredor, no cambia la columna corredor_id en inmueble
        corredorRepository.save(corredor);

        return ResponseEntity.ok("Corredor assigned to property successfully.");
    }




}
