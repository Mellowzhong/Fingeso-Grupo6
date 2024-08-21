package com.example.BackendHabitatDigital.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
/*
    Descripcion: Esta clase representa la entidad `CorredorEntity` que se almacena en la base de datos
    en la tabla `corredor`. La entidad `CorredorEntity` está asociada a un usuario específico a través
    de una relación de uno a uno con la entidad `UserEntity` y tiene una relación de uno a muchos
    con la entidad `InmuebleEntity`, lo que representa los inmuebles gestionados por el corredor.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "corredor")
public class CorredorEntity {
    /*
        Descripcion: Campo que representa el identificador único de la entidad `CorredorEntity`.
        Es la clave primaria de la tabla `corredor` en la base de datos. El valor es generado
        automáticamente mediante la estrategia `GenerationType.IDENTITY`.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer valoracion;

    /*
        Descripcion: Relación uno a uno con la entidad `UserEntity`, representando que cada
        corredor está asociado a un único usuario. La columna `user_id` en la tabla
        `corredor` se utiliza como clave externa para referenciar la entidad `UserEntity`.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /*
        Descripcion: Relación uno a muchos con la entidad `InmuebleEntity`, representando
        que un corredor puede estar asociado a múltiples inmuebles. La relación está mapeada
        a través del campo `id` en `InmuebleEntity`.
     */
    @OneToMany(mappedBy = "id")
    private List<InmuebleEntity> inmuebles;
}
