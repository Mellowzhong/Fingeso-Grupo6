package com.example.BackendHabitatDigital.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Descripcion: Esta clase representa la entidad `TenantEntity` que se almacena en la base de datos
    en la tabla `tenant`. La entidad `TenantEntity` representa a un arrendatario o inquilino que
    está asociado con un inmueble específico y un usuario en el sistema.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tenant")
public class TenantEntity {

    /*
        Descripcion: Campo que representa el identificador único de la entidad `TenantEntity`.
        Es la clave primaria de la tabla `tenant` en la base de datos. El valor es generado
        automáticamente mediante la estrategia `GenerationType.IDENTITY`.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Descripcion: Relación uno a uno con la entidad `PropertyEntity`, representando que cada
        arrendatario está asociado a un único inmueble. La columna `inmueble_id` en la tabla
        `tenant` se utiliza como clave externa para referenciar la entidad `PropertyEntity`.
     */
    @OneToOne
    @JoinColumn(name = "inmueble_id")
    private PropertyEntity inmueble;

    /*
        Descripcion: Relación uno a uno con la entidad `UserEntity`, representando que cada
        arrendatario está asociado a un único usuario. La columna `user_id` en la tabla
        `tenant` se utiliza como clave externa para referenciar la entidad `UserEntity`.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
