package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.OwnerEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<OwnerEntity,Long> {
    Optional<OwnerEntity> findByUserId(Long userId);
}
