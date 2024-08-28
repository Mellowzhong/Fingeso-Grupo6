package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.CorredorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorredorRepository extends JpaRepository<CorredorEntity,Long> {
    Optional<CorredorEntity> findCorredorByUserId(Long aLong);
    Optional<CorredorEntity> findCorredorById(Long aLong);


}
