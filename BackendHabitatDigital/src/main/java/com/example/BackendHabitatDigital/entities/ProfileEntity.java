package com.example.BackendHabitatDigital.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*
    Descripcion: Esta clase representa la entidad `ProfileEntity` que se almacena en la base de datos
    en la tabla `profiles`. La entidad `ProfileEntity` contiene información personal relacionada
    con un perfil de usuario, como la foto, nombre, apellido, descripción y datos de contacto.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
public class ProfileEntity {
    /*
        Descripcion: Campo que representa el identificador único de la entidad `ProfileEntity`.
        Es la clave primaria de la tabla `profiles` en la base de datos. El valor es generado
        automáticamente mediante la estrategia `GenerationType.IDENTITY`.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photo;

    private String firstname;

    private String lastname;

    private String description;

    private String contact;

}