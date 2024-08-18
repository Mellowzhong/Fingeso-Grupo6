package com.example.BackendHabitatDigital.services;

import com.example.BackendHabitatDigital.entities.CorredorEntity;
import com.example.BackendHabitatDigital.entities.RoleEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.repositories.CorredorRepository;
import com.example.BackendHabitatDigital.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorredorService {
    private final CorredorRepository corredorRepository;
    private final UserRepository userRepository;

    @Autowired
    public CorredorService(CorredorRepository corredorRepository, UserRepository userRepository) {
        this.corredorRepository = corredorRepository;
        this.userRepository = userRepository;
    }


    public ResponseEntity<Object> addCorredor(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(email);

        if (optionalUser.isEmpty()){
            return new ResponseEntity<>("no existe el usuario", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = optionalUser.get();
        user.setRole(RoleEntity.CORREDOR);

        CorredorEntity corredor = new CorredorEntity();
        corredor.setUser(user);
        corredor.setValoracion(-1);

        corredorRepository.save(corredor);

        return new ResponseEntity<>("se guardo con exito", HttpStatus.CREATED);
    }

    public List<CorredorEntity> getAllCorredores() {
         return this.corredorRepository.findAll();
    }

    public Optional<CorredorEntity> getCorredorById(long corredorId) {
        return this.corredorRepository.findById(corredorId);
    }
}
