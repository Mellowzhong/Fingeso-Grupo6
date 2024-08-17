package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<TenantEntity,Long> {
}
