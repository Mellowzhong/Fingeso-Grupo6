package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {
}
