package com.example.BackendHabitatDigital.services;

import com.example.BackendHabitatDigital.entities.*;
import com.example.BackendHabitatDigital.repositories.CorredorRepository;
import com.example.BackendHabitatDigital.repositories.PropertyRepository;
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
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final CorredorRepository corredorRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, UserRepository userRepository, OwnerRepository ownerRepository, CorredorRepository corredorRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.corredorRepository = corredorRepository;
    }

    public ResponseEntity<PropertyEntity> addProperty(PropertyEntity inmueble, String userEmail) {
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
            propertyRepository.save(inmueble); // Guardar el inmueble en la base de datos
            return new ResponseEntity<>(inmueble, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    public List<PropertyEntity> getAllInmuebles() {
        return this.propertyRepository.findAll();
    }

    public Optional<PropertyEntity> getInmuebleById(long inmuebleId) {
        return this.propertyRepository.findById(inmuebleId);
    }

    public ResponseEntity<Object> deleteInmueble(long inmuebleId) {
        this.propertyRepository.deleteById(inmuebleId);
        return new ResponseEntity<>("Se eliminó con éxito", HttpStatus.OK);
    }

    public List<PropertyEntity> findAllInmueblesByOwner(long userId) {
        Optional<OwnerEntity> owner = ownerRepository.findByUserId(userId);
        return owner.map(value -> this.propertyRepository.findAllByOwner(value).orElse(List.of())).orElse(List.of());
    }

    public ProfileEntity getOwnerProfile(long inmuebleId) {
        Optional<PropertyEntity> inmuebleO = propertyRepository.findById(inmuebleId);
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
        PropertyEntity inmueble = propertyRepository.findById(inmuebleId)
                .orElseThrow(() -> new EntityNotFoundException("Inmueble not found with ID: " + inmuebleId));

        // Verificar que el usuario autenticado es el propietario del inmueble
        if (!inmueble.getOwner().equals(owner)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to modify this property.");
        }

        // Obtener el corredor por su ID
        CorredorEntity corredor = corredorRepository.findById(corredorId)
                .orElseThrow(() -> new EntityNotFoundException("Corredor not found with ID: " + corredorId));

        // Agregar el ID del inmueble a la lista de inmuebles pendientes del corredor SIN cambiar el corredor_id del inmueble
        //corredor.getInmueblesPendientes().add(inmueble.getId());

        // Guardar el corredor con la lista de inmuebles pendientes actualizada
        corredorRepository.save(corredor);

        return ResponseEntity.ok("Inmueble assigned to corredor's pending list successfully without changing corredor_id.");
    }

    /*
        Descripcion: Este método obtiene todos los inmuebles que se encuentran disponibles
    */
    public List<PropertyEntity> getAllinmeblesDisponibles(){
        List<PropertyEntity> inmuebles = this.getAllInmuebles();
        List<PropertyEntity> inmueblesDisponibles = new ArrayList<>();
        for (PropertyEntity inmueble : inmuebles){
            if (inmueble.getAvailable() == true){
                inmueblesDisponibles.add(inmueble);
            }
        }
        Collections.sort(inmueblesDisponibles, new Comparator<PropertyEntity>() {
            @Override
            public int compare(PropertyEntity i1, PropertyEntity i2) {
                return i2.getId().compareTo(i1.getId()); // from highest to lowest ID
            }
        });
        return inmueblesDisponibles;
    }

    public PropertyEntity updateInmueble(PropertyEntity updatedInmueble) {
        // Buscar el inmueble por ID
        PropertyEntity existingInmueble = propertyRepository.findById(updatedInmueble.getId())
                .orElseThrow(() -> new EntityNotFoundException("Inmueble no encontrado"));

        // Actualizar los campos modificables del inmueble
        existingInmueble.setAvailable(updatedInmueble.getAvailable());
        existingInmueble.setSale(updatedInmueble.getSale());
        existingInmueble.setPrice(updatedInmueble.getPrice());
        existingInmueble.setAddress(updatedInmueble.getAddress());
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
        return propertyRepository.save(existingInmueble);
    }
}

