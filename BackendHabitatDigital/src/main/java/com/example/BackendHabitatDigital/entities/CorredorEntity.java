package com.example.BackendHabitatDigital.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    // Lista de inmuebles oficialmente asignados al corredor
    @OneToMany(mappedBy = "corredor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InmuebleEntity> inmuebles = new ArrayList<>();

    // Nueva lista de IDs de inmuebles pendientes de aceptación
    @ElementCollection
    @CollectionTable(name = "corredor_inmuebles_pendientes", joinColumns = @JoinColumn(name = "corredor_id"))
    @Column(name = "inmueble_id")
    private List<Long> inmueblesPendientes = new ArrayList<>();
}
