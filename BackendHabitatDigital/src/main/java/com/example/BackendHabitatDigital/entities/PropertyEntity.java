package com.example.BackendHabitatDigital.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
    Descripcion: Esta clase representa la entidad `PropertyEntity` que se almacena en la base de datos
    en la tabla `inmueble`. La entidad `PropertyEntity` representa un inmueble con detalles específicos
    como disponibilidad, tipo de venta, precio, dirección, número de habitaciones y baños, entre otros.
    También define relaciones con las entidades `OwnerEntity` y `CorredorEntity`.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inmueble")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PropertyEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean available;

    private Boolean sale;

    private Integer price;

    private String address;

    private String type;

    private Integer rooms;

    private Integer bathrooms;

    private Integer squareMeters;

    private Integer yearConstruction;

    private String state;

    private String description;

    @ElementCollection
    private List<String> photos;

    private String services;

    private Boolean parking;

    private Boolean furnished;

    private Boolean approved;

    /*
        Descripcion: Relación muchos a uno con la entidad `OwnerEntity`, representando que un
        propietario puede tener múltiples inmuebles. La columna `owner_id` en la tabla `inmueble`
        se utiliza como clave externa para referenciar la entidad `OwnerEntity`.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private OwnerEntity owner;

    /*
        Descripcion: Relación uno a uno con la entidad `CorredorEntity`, representando que un
        inmueble puede estar asociado a un único corredor. La columna `corredor_id` en la tabla
        `inmueble` se utiliza como clave externa para referenciar la entidad `CorredorEntity`.
     */
    @ManyToOne
    @JoinColumn(name = "corredor_id", nullable = true) // Este campo puede ser NULL
    @JsonBackReference
    private CorredorEntity corredor;
}
