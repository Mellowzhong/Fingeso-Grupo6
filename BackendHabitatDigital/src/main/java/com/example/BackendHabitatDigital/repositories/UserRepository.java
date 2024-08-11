package com.example.BackendHabitatDigital.repositories;
import com.example.BackendHabitatDigital.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
