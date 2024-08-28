package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.CorredorEntity;
import com.example.BackendHabitatDigital.entities.InmuebleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CorredorRepository extends JpaRepository<CorredorEntity,Long> {
    Optional<CorredorEntity> findCorredorByUserId(Long aLong);
    Optional<CorredorEntity> findCorredorById(Long aLong);

}
