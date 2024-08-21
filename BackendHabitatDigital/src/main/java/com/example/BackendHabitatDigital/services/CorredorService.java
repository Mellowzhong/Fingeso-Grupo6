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

/*
    Descripcion: Esta clase `CorredorService` es un servicio que gestiona la lógica de negocio
    relacionada con los corredores (brokers) en el sistema. Proporciona métodos para agregar nuevos
    corredores, obtener corredores existentes y gestionar las propiedades asociadas a los corredores.
 */
@Service
public class CorredorService {
    private final CorredorRepository corredorRepository;
    private final UserRepository userRepository;
    // Just for access to the list of properties that the corredor would be able to take
    private final InmuebleRepository inmuebleRepository;

    /*
        Descripcion: Constructor de la clase `CorredorService` que inyecta los repositorios necesarios
        para manejar las operaciones relacionadas con corredores, usuarios y propiedades.
     */
    @Autowired
    public CorredorService(CorredorRepository corredorRepository, UserRepository userRepository, InmuebleRepository inmuebleRepository) {
        this.corredorRepository = corredorRepository;
        this.userRepository = userRepository;
        this.inmuebleRepository = inmuebleRepository;
    }

    /*
        Descripcion: Este método agrega un nuevo corredor en el sistema. Busca un usuario existente
        por correo electrónico, le asigna el rol de corredor, y guarda la nueva entidad `CorredorEntity`.
     */
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

    /*
        Descripcion: Este método obtiene una lista de todos los corredores registrados en el sistema.
     */
    public List<CorredorEntity> getAllCorredores() {
        return this.corredorRepository.findAll();
    }

    /*
        Descripcion: Este método obtiene un corredor a partir de su ID.
     */
    public Optional<CorredorEntity> getCorredorById(long corredorId) {
        return this.corredorRepository.findById(corredorId);
    }

    /*
        Descripcion: Este método obtiene todas las propiedades asociadas al corredor autenticado.
        Extrae la información de autenticación actual, verifica que el usuario sea un corredor,
        y devuelve la lista de inmuebles asociados a ese corredor.
     */
    public List<InmuebleEntity> getInmueblesByAuthenticatedCorredor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        CorredorEntity corredor = corredorRepository.findCorredorById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor with user ID " + currentUserId + " not found"));

        return corredor.getInmuebles();
    }

    /*
        Descripcion: Este método obtiene todas las propiedades que no tienen un corredor asignado
        y son visibles para el corredor autenticado. Verifica que el usuario sea un corredor autorizado
        y devuelve la lista de inmuebles sin corredor asignado.
     */
    public List<InmuebleEntity> getInmueblesWithoutCorredorForAuthenticatedCorredor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long currentUserId = ((UserEntity) authentication.getPrincipal()).getId();

        CorredorEntity corredor = corredorRepository.findCorredorById(currentUserId)
                .orElseThrow(() -> new SecurityException("User is not authorized or not a corredor"));

        return inmuebleRepository.findByCorredorIsNull();
    }

}