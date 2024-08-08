package com.example.BackendHabitatDigital.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agendamiento")
public class SchedulingEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCreation;

    private Date date;

    private LocalTime time;

    private String state;

    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    @JsonIgnore
    private InmuebleEntity inmueble;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private OwnerEntity owner;

    @ManyToOne
    @JoinColumn(name = "corredor_id")
    @JsonIgnore
    private CorredorEntity corredor;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;
}
