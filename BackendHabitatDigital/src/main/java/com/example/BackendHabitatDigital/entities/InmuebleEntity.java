package com.example.BackendHabitatDigital.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inmueble")
public class InmuebleEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean disponibility;

    private String sale;

    private Integer price;

    private String direction;

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

    private Boolean aprobated;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private OwnerEntity owner;

    @OneToOne
    @JoinColumn(name = "corredor_id")
    private CorredorEntity corredor;
}
