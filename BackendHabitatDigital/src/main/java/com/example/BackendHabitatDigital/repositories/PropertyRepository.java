package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.PropertyEntity;
import com.example.BackendHabitatDigital.entities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<PropertyEntity,Long> {
    Optional<List<PropertyEntity>> findAllByOwner(OwnerEntity owner);
}
