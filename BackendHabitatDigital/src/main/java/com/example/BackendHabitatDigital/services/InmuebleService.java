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

import java.util.*;

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


    public ResponseEntity<String> requestCorredorToInmueble(Long inmuebleId, Long corredorId) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentOwnerUsername = authentication.getName(); // Esto asume que el nombre de usuario es el email

        // Usar OwnerRepository para encontrar el OwnerEntity basado en el email del usuario
        OwnerEntity owner = ownerRepository.findByUserEmail(currentOwnerUsername)
                .orElseThrow(() -> new SecurityException("User is not authorized or not an owner"));

        // Obtener el inmueble por su ID
        InmuebleEntity inmueble = inmuebleRepository.findById(inmuebleId)
                .orElseThrow(() -> new EntityNotFoundException("Inmueble not found with ID: " + inmuebleId));

        // Verificar que el usuario autenticado es el propietario del inmueble
        if (!inmueble.getOwner().equals(owner)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to modify this property.");
        }

        // Obtener el corredor por su ID
        CorredorEntity corredor = corredorRepository.findById(corredorId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor not found with ID: " + corredorId));

        // Agregar el ID del inmueble a la lista de inmuebles pendientes del corredor SIN cambiar el corredor_id del inmueble
        corredor.getInmueblesPendientes().add(inmueble.getId());

        // Guardar el corredor con la lista de inmuebles pendientes actualizada
        corredorRepository.save(corredor);

        return ResponseEntity.ok("Inmueble assigned to corredor's pending list successfully without changing corredor_id.");
    }

    /*
        Descripcion: Este método obtiene todos los inmuebles que se encuentran disponibles
    */
    public List<InmuebleEntity> getAllinmeblesDisponibles(){
        List<InmuebleEntity> inmuebles = this.getAllInmuebles();
        List<InmuebleEntity> inmueblesDisponibles = new ArrayList<>();
        for (InmuebleEntity inmueble : inmuebles){
            if (inmueble.getDisponibility() == true){
                inmueblesDisponibles.add(inmueble);
            }
        }
        Collections.sort(inmueblesDisponibles, new Comparator<InmuebleEntity>() {
            @Override
            public int compare(InmuebleEntity i1, InmuebleEntity i2) {
                return i2.getId().compareTo(i1.getId()); // from highest to lowest ID
            }
        });
        return inmueblesDisponibles;
    }

    public InmuebleEntity updateInmueble(InmuebleEntity updatedInmueble) {
        // Buscar el inmueble por ID
        InmuebleEntity existingInmueble = inmuebleRepository.findById(updatedInmueble.getId())
                .orElseThrow(() -> new EntityNotFoundException("Inmueble no encontrado"));

        // Actualizar los campos modificables del inmueble
        existingInmueble.setDisponibility(updatedInmueble.getDisponibility());
        existingInmueble.setSale(updatedInmueble.getSale());
        existingInmueble.setPrice(updatedInmueble.getPrice());
        existingInmueble.setDirection(updatedInmueble.getDirection());
        existingInmueble.setType(updatedInmueble.getType());
        existingInmueble.setRooms(updatedInmueble.getRooms());
        existingInmueble.setBathrooms(updatedInmueble.getBathrooms());
        existingInmueble.setSquareMeters(updatedInmueble.getSquareMeters());
        existingInmueble.setYearConstruction(updatedInmueble.getYearConstruction());
        existingInmueble.setState(updatedInmueble.getState());
        existingInmueble.setDescription(updatedInmueble.getDescription());
        existingInmueble.setPhotos(updatedInmueble.getPhotos());
        existingInmueble.setServices(updatedInmueble.getServices());
        existingInmueble.setParking(updatedInmueble.getParking());
        existingInmueble.setFurnished(updatedInmueble.getFurnished());
        existingInmueble.setApproved(updatedInmueble.getApproved());
        existingInmueble.setCorredor(updatedInmueble.getCorredor());

        // Guardar el inmueble actualizado
        return inmuebleRepository.save(existingInmueble);
    }
}

