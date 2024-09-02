package com.example.BackendHabitatDigital.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/*
    Descripcion: Esta clase representa la entidad `OwnerEntity` que se almacena en la base de datos
    en la tabla `owner`. La entidad `OwnerEntity` está asociada a un usuario específico a través
    de una relación de uno a uno con la entidad `UserEntity` y tiene una relación de uno a muchos
    con la entidad `PropertyEntity`, lo que representa los inmuebles que posee el propietario.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "owner")
public class OwnerEntity{

    /*
        Descripcion: Campo que representa el identificador único de la entidad `OwnerEntity`.
        Es la clave primaria de la tabla `owner` en la base de datos. El valor es generado
        automáticamente mediante la estrategia `GenerationType.IDENTITY`.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Descripcion: Relación uno a uno con la entidad `UserEntity`, representando que cada
        propietario está asociado a un único usuario. La columna `user_id` en la tabla
        `owner` se utiliza como clave externa para referenciar la entidad `UserEntity`.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /*
        Descripcion: Relación uno a muchos con la entidad `PropertyEntity`, representando
        que un propietario puede poseer múltiples inmuebles. Esta relación está mapeada
        a través del campo `owner` en `PropertyEntity`.
     */
    @OneToMany(mappedBy = "owner")
    private List<PropertyEntity> inmuebles = new ArrayList<>();
}
