package com.example.BackendHabitatDigital.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Descripción: Esta clase `RegisterCorredorRequest` se utiliza para encapsular la información
    necesaria para realizar una solicitud de registro de un nuevo usuario corredor en el sistema.
    Contiene detalles específicos del corredor, así como la información necesaria para crear
    su cuenta de usuario y perfil.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCorredorRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String contact;
    private byte[] picture;
    private String description;
    private Integer valoracion;  // Campo específico para corredores
}

