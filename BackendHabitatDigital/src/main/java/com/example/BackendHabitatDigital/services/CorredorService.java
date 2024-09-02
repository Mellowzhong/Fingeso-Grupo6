package com.example.BackendHabitatDigital.services;

import com.example.BackendHabitatDigital.entities.CorredorEntity;
import com.example.BackendHabitatDigital.entities.PropertyEntity;
import com.example.BackendHabitatDigital.entities.RoleEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.repositories.CorredorRepository;
import com.example.BackendHabitatDigital.repositories.PropertyRepository;
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
    private final PropertyRepository propertyRepository;

    @Autowired
    public CorredorService(CorredorRepository corredorRepository, UserRepository userRepository, PropertyRepository propertyRepository) {
        this.corredorRepository = corredorRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
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

    // Todos los inmuebles asignados del corredor
    public List<PropertyEntity> getAssignedProperties() {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        // Encontrar al corredor por el ID del usuario autenticado
        CorredorEntity corredor = corredorRepository.findCorredorByUserId(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor with user ID " + currentUserId + " not found"));

        // Retornar la lista de inmuebles asignados
        return corredor.getInmuebles();
    }
    public List<Long> getInmueblesPendientes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        // Encuentra al corredor basado en el usuario actual
        CorredorEntity corredor = corredorRepository.findCorredorByUserId(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor with user ID " + currentUserId + " not found"));

        // Retorna la lista de IDs de inmuebles pendientes
        return corredor.getInmueblesPendientes();
    }

    public List<PropertyEntity> getInmueblesPendientesCompleta() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        // Encuentra al corredor basado en el usuario actual
        CorredorEntity corredor = corredorRepository.findCorredorByUserId(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor with user ID " + currentUserId + " not found"));

        // Obtener los IDs de inmuebles pendientes
        List<Long> inmueblesPendientesIds = corredor.getInmueblesPendientes();

        // Obtener la información completa de los inmuebles usando los IDs pendientes
        return propertyRepository.findAllById(inmueblesPendientesIds);
    }

    public ResponseEntity<String> acceptProperty(Long inmuebleId) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        // Encontrar al corredor por el ID del usuario autenticado
        CorredorEntity corredor = corredorRepository.findCorredorByUserId(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor with user ID " + currentUserId + " not found"));

        // Verificar que el inmueble esté en la lista de pendientes del corredor
        if (!corredor.getInmueblesPendientes().contains(inmuebleId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inmueble not found in pending list.");
        }

        // Obtener el inmueble por su ID
        PropertyEntity inmueble = propertyRepository.findById(inmuebleId)
                .orElseThrow(() -> new EntityNotFoundException("Inmueble not found with ID: " + inmuebleId));

        // Eliminar el inmueble de la lista de pendientes y agregarlo a la lista de inmuebles del corredor
        corredor.getInmueblesPendientes().remove(inmuebleId);
        corredor.getInmuebles().add(inmueble);
        inmueble.setCorredor(corredor); // Asignar el corredor al inmueble
        corredorRepository.save(corredor);
        propertyRepository.save(inmueble); // Guardar el cambio en el inmueble también

        return ResponseEntity.ok("Inmueble accepted successfully.");
    }

    public ResponseEntity<String> rejectProperty(Long inmuebleId) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        // Encontrar al corredor por el ID del usuario autenticado
        CorredorEntity corredor = corredorRepository.findCorredorByUserId(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor with user ID " + currentUserId + " not found"));

        // Verificar que el inmueble esté en la lista de pendientes del corredor
        if (!corredor.getInmueblesPendientes().contains(inmuebleId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inmueble not found in pending list.");
        }

        // Eliminar el inmueble de la lista de pendientes
        corredor.getInmueblesPendientes().remove(inmuebleId);
        corredorRepository.save(corredor);

        return ResponseEntity.ok("Inmueble rejected successfully.");
    }
}
