package com.example.BackendHabitatDigital.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Descripcion: Esta clase representa la entidad `AdminEntity` que se almacena en la base de datos
    en la tabla `admin`. La entidad `AdminEntity` está asociada a un usuario específico a través
    de una relación de uno a uno con la entidad `UserEntity`.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin")
public class AdminEntity {

    /*
        Descripcion: Campo que representa el identificador único de la entidad `AdminEntity`.
        Es la clave primaria de la tabla `admin` en la base de datos. El valor es generado
        automáticamente mediante la estrategia `GenerationType.IDENTITY`.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Descripcion: Relación uno a uno con la entidad `UserEntity`, representando que cada
        administrador está asociado a un único usuario. La columna `user_id` en la tabla
        `admin` se utiliza como clave externa para referenciar la entidad `UserEntity`.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
