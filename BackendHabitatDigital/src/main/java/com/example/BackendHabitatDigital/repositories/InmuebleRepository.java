package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.CorredorEntity;
import com.example.BackendHabitatDigital.entities.InmuebleEntity;
import com.example.BackendHabitatDigital.entities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InmuebleRepository extends JpaRepository<InmuebleEntity,Long> {
    Optional<List<InmuebleEntity>> findAllByOwner(OwnerEntity owner);
    List<InmuebleEntity> findByCorredorId(Long corredorId);
    List<InmuebleEntity> findByCorredorIsNull();


    // Consulta personalizada para obtener inmuebles que están en la lista de propiedades de un corredor
    // y que aún no han sido aceptados (corredor es null)
    @Query("SELECT i FROM InmuebleEntity i WHERE i.corredor IS NULL AND i IN :inmuebles")
    List<InmuebleEntity> findByCorredorIsNullAndInmueblesIn(@Param("inmuebles") List<InmuebleEntity> inmuebles);
}
