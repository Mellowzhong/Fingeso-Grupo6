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

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final InmuebleRepository inmuebleRepository;

    private final CorredorRepository corredorRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, UserRepository userRepository, InmuebleRepository inmuebleRepository, CorredorRepository corredorRepository) {
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
        this.inmuebleRepository = inmuebleRepository;
        this.corredorRepository = corredorRepository;
    }

    public ResponseEntity<Object> addOwner(InmuebleRequest inmuebleForm) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(inmuebleForm.getUserEmail());

        if (optionalUser.isEmpty()){
            return new ResponseEntity<>("The user doesn't exist", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = optionalUser.get();
        Optional<OwnerEntity> optionalOwner = ownerRepository.findById(user.getId());
        OwnerEntity owner = new OwnerEntity();

        InmuebleEntity inmueble = new InmuebleEntity();

        inmueble.setDisponibility(inmuebleForm.getSale());
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
        inmueble.setAprobated(inmuebleForm.getAprobated());
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

    public List<OwnerEntity> getAllOwners() {
        return this.ownerRepository.findAll();
    }

    public Optional<OwnerEntity> getOwnerById(long ownerId) {
        return this.ownerRepository.findById(ownerId);
    }

    public Optional<OwnerEntity> findOwnerByUserId(Long userId) {
        return ownerRepository.findById(userId);
    }
}