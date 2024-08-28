package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.OwnerEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {

    // Busca el OwnerEntity usando una referencia a UserEntity
    Optional<OwnerEntity> findByUser(UserEntity user);

    // Busca el OwnerEntity por el ID del UserEntity asociado
    Optional<OwnerEntity> findByUserId(Long userId);

    // Este método necesita ser corregido ya que OwnerEntity no tiene directamente un campo email
    // Utilizamos una query para buscar el OwnerEntity basado en el email del UserEntity
    @Query("SELECT o FROM OwnerEntity o WHERE o.user.username = :email")
    Optional<OwnerEntity> findByUserEmail(@Param("email") String email);
}
