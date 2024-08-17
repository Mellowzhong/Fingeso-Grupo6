package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.InmuebleEntity;
import com.example.BackendHabitatDigital.entities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InmuebleRepository extends JpaRepository<InmuebleEntity,Long> {
    Optional<List<InmuebleEntity>> findAllByOwner(OwnerEntity owner);
}
