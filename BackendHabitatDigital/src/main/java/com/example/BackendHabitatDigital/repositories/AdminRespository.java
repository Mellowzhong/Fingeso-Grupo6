package com.example.BackendHabitatDigital.repositories;

import com.example.BackendHabitatDigital.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    Descripcion: En este paquete se encuentran todas las conexiones mediante JPA con la base de datos.
*/
public interface AdminRespository extends JpaRepository<AdminEntity, Long> {
}
