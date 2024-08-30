package com.example.BackendHabitatDigital.services;

import com.example.BackendHabitatDigital.Requests.InmuebleRequest;
import com.example.BackendHabitatDigital.entities.*;
import com.example.BackendHabitatDigital.repositories.CorredorRepository;
import com.example.BackendHabitatDigital.repositories.InmuebleRepository;
import com.example.BackendHabitatDigital.repositories.OwnerRepository;
import com.example.BackendHabitatDigital.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
    Descripcion: Esta clase `OwnerService` es un servicio que gestiona la lógica de negocio relacionada
    con los propietarios (owners) en el sistema. Proporciona métodos para agregar nuevos propietarios,
    obtener propietarios existentes y gestionar las relaciones entre los propietarios y sus inmuebles.
 */
@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final InmuebleRepository inmuebleRepository;

    private final CorredorRepository corredorRepository;

    /*
        Descripcion: Constructor de la clase `OwnerService` que inyecta los repositorios necesarios
        para manejar las operaciones relacionadas con propietarios, usuarios, inmuebles y corredores.
     */
    @Autowired
    public OwnerService(OwnerRepository ownerRepository, UserRepository userRepository, InmuebleRepository inmuebleRepository, CorredorRepository corredorRepository) {
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
        this.inmuebleRepository = inmuebleRepository;
        this.corredorRepository = corredorRepository;
    }

    /*
        Descripcion: Este método agrega un nuevo propietario al sistema o asocia un inmueble a un propietario
        existente. Verifica si el usuario proporcionado ya tiene una entidad `OwnerEntity` asociada. Si no es así,
        crea una nueva entidad de propietario. Luego, asocia el inmueble con el propietario y, opcionalmente,
        con un corredor si se proporciona el correo electrónico de un corredor.
     */
    public ResponseEntity<Object> addOwner(InmuebleRequest inmuebleForm) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(inmuebleForm.getUserEmail());

        if (optionalUser.isEmpty()){
            return new ResponseEntity<>("The user doesn't exist", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = optionalUser.get();
        Optional<OwnerEntity> optionalOwner = ownerRepository.findById(user.getId());
        OwnerEntity owner = new OwnerEntity();

        InmuebleEntity inmueble = new InmuebleEntity();

        inmueble.setDisponibility(Boolean.TRUE);
        inmueble.setPrice(inmuebleForm.getPrice());
        inmueble.setDirection(inmuebleForm.getDirection());
        inmueble.setType(inmuebleForm.getType());
        inmueble.setRooms(inmuebleForm.getRooms());
        inmueble.setBathrooms(inmuebleForm.getBathrooms());
        inmueble.setSquareMeters(inmuebleForm.getSquareMeters());
        inmueble.setYearConstruction(inmuebleForm.getYearConstruction());
        inmueble.setState(inmuebleForm.getState());
        inmueble.setDescription(inmuebleForm.getDescription());
        inmueble.setServices(inmuebleForm.getServices());
        inmueble.setParking(inmuebleForm.getParking());
        inmueble.setFurnished(inmuebleForm.getFurnished());
        inmueble.setApproved(inmuebleForm.getAprobated());
        inmueble.setPhotos(inmuebleForm.getPhotos());

        if (optionalOwner.isPresent()){
            owner = optionalOwner.get();
            inmueble.setOwner(owner);
        } else {
            user.setRole(RoleEntity.OWNER);
            owner.setUser(user);
            inmueble.setOwner(owner);
        }

        if (inmuebleForm.getCorredorEmail() != null){
            Optional<UserEntity> optionalUserCorredor = userRepository.findByUsername(inmuebleForm.getCorredorEmail());
            if (optionalUserCorredor.isEmpty()){
                return new ResponseEntity<>("no existe el corredor", HttpStatus.BAD_REQUEST);
            }
            UserEntity userCorredor = optionalUserCorredor.get();
            Optional<CorredorEntity> optionalCorredor = corredorRepository.findById(userCorredor.getId());
            if (optionalCorredor.isEmpty()){
                return new ResponseEntity<>("ese usuario no es un corredor", HttpStatus.BAD_REQUEST);
            }
            CorredorEntity corredor = optionalCorredor.get();
            corredor.getInmuebles().add(inmueble);
            corredorRepository.save(corredor);
        }

        owner.getInmuebles().add(inmueble);
        ownerRepository.save(owner);  // Esto guardará tanto el owner como el inmueble
        inmuebleRepository.save(inmueble);

        if (optionalOwner.isPresent()){
            return new ResponseEntity<>("se guardo con exito en el owner ya existente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("se guardo con exito", HttpStatus.CREATED);
        }
    }

    /*
        Descripcion: Este método obtiene una lista de todos los propietarios registrados en el sistema.
     */
    public List<OwnerEntity> getAllOwners() {
        return this.ownerRepository.findAll();
    }

    /*
        Descripcion: Este método obtiene un propietario específico por su ID.
     */
    public Optional<OwnerEntity> getOwnerById(long ownerId) {
        return this.ownerRepository.findById(ownerId);
    }

    /*
        Descripcion: Este método busca un propietario específico por el ID del usuario.
     */
    public Optional<OwnerEntity> findOwnerByUserId(Long userId) {
        return ownerRepository.findById(userId);
    }
}