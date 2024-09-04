package com.example.BackendHabitatDigital.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

/*
    Descripcion: Esta clase representa la entidad `SchedulingEntity` que se almacena en la base de datos
    en la tabla `agendamiento`. La entidad `SchedulingEntity` contiene información sobre la programación
    de eventos o citas relacionadas con un inmueble, incluyendo la fecha, hora, estado y las relaciones
    con las entidades `PropertyEntity`, `OwnerEntity`, `CorredorEntity`, y `UserEntity`.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agendamiento")
public class SchedulingEntity {
    /*
        Descripcion: Campo que representa el identificador único de la entidad `SchedulingEntity`.
        Es la clave primaria de la tabla `agendamiento` en la base de datos. El valor es generado
        automáticamente mediante la estrategia `GenerationType.IDENTITY`.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCreation;

    private Date date;

    private LocalTime time;

    private String state;

    /*
        Descripcion: Relación muchos a uno con la entidad `PropertyEntity`, representando que un agendamiento
        está asociado a un inmueble específico. La columna `inmueble_id` en la tabla `agendamiento`
        se utiliza como clave externa para referenciar la entidad `PropertyEntity`.
     */
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    @JsonIgnore
    private PropertyEntity inmueble;

    /*
        Descripcion: Relación muchos a uno con la entidad `OwnerEntity`, representando que un agendamiento
        está asociado a un propietario específico. La columna `owner_id` en la tabla `agendamiento`
        se utiliza como clave externa para referenciar la entidad `OwnerEntity`.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private OwnerEntity owner;

    /*
        Descripcion: Relación muchos a uno con la entidad `CorredorEntity`, representando que un agendamiento
        está asociado a un corredor específico. La columna `corredor_id` en la tabla `agendamiento`
        se utiliza como clave externa para referenciar la entidad `CorredorEntity`.
     */
    @ManyToOne
    @JoinColumn(name = "corredor_id")
    @JsonIgnore
    private CorredorEntity corredor;

    /*
        Descripcion: Relación uno a uno con la entidad `UserEntity`, representando que un agendamiento
        está asociado a un usuario específico que creó o está involucrado en el agendamiento.
        La columna `user_id` en la tabla `agendamiento` se utiliza como clave externa para referenciar
        la entidad `UserEntity`.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;
}
