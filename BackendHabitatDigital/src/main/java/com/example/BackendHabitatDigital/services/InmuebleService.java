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

/*
    Descripcion: Esta clase `InmuebleService` es un servicio que gestiona la lógica de negocio relacionada
    con los inmuebles en el sistema. Proporciona métodos para agregar, obtener, y eliminar inmuebles, así como
    para gestionar las relaciones entre los inmuebles y sus propietarios.
 */
@Service
public class InmuebleService {

    private final InmuebleRepository inmuebleRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    /*
        Descripcion: Constructor de la clase `InmuebleService` que inyecta los repositorios necesarios
        para manejar las operaciones relacionadas con inmuebles, usuarios y propietarios.
     */
    @Autowired
    public InmuebleService(InmuebleRepository inmuebleRepository, UserRepository userRepository, OwnerRepository ownerRepository) {
        this.inmuebleRepository = inmuebleRepository;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
    }

    /*
        Descripcion: Este método agrega un nuevo inmueble al sistema y lo asocia con un propietario.
        Verifica si el usuario proporcionado ya tiene una entidad `OwnerEntity` asociada. Si no es así,
        crea una nueva entidad de propietario. Luego, asocia el inmueble con el propietario y lo guarda en el repositorio.
     */
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

    /*
        Descripcion: Este método obtiene una lista de todos los inmuebles registrados en el sistema.
     */
    public List<InmuebleEntity> getAllInmuebles() {
        return this.inmuebleRepository.findAll();
    }

    public Optional<InmuebleEntity> getInmuebleById(long productId) {
        return this.inmuebleRepository.findById(productId);
    }

    /*
        Descripcion: Este método obtiene un inmueble específico por su ID.
     */
    public ResponseEntity<Object> deleteInmueble(long inmuebleId) {
        this.inmuebleRepository.deleteById(inmuebleId);
        return new ResponseEntity<>("Se eliminó con éxito", HttpStatus.OK);
    }

    /*
        Descripcion: Este método obtiene todos los inmuebles asociados a un propietario específico
        por el ID del propietario.
     */
    public List<InmuebleEntity> findAllInmueblesByOwner(long userId) {
        Optional<OwnerEntity> owner = ownerRepository.findById(userId);
        Optional<List<InmuebleEntity>> inmuebles = this.inmuebleRepository.findAllByOwner(owner.get());
        return inmuebles.get();

    }

    /*
        Descripcion: Este método obtiene el perfil del propietario de un inmueble específico
        por el ID del inmueble.
     */
    public ProfileEntity getOwnerProfile(long inmuebleid){
        Optional<InmuebleEntity> inmuebleO= inmuebleRepository.findById(inmuebleid);
        InmuebleEntity inmueble = inmuebleO.get();
        return inmueble.getOwner().getUser().getProfile();
    }

    /*
        Descripcion: Este método obtiene todos los inmuebles que no tienen un corredor asignado
        en el sistema.
     */
    public List<InmuebleEntity> findInmueblesWithoutCorredor() {
        return inmuebleRepository.findByCorredorIsNull();
    }
}
