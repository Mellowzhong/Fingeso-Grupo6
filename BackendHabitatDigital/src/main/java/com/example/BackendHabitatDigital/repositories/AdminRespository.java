package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRespository extends JpaRepository<AdminEntity, Long> {
}
