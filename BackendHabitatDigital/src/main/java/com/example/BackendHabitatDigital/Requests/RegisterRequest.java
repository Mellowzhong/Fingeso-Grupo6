package com.example.BackendHabitatDigital.Requests;

import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.entities.RoleEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Descripcion: Esta clase `RegisterRequest` se utiliza para encapsular la información necesaria
    para realizar una solicitud de registro de un nuevo usuario en el sistema. Contiene detalles personales
    del usuario, así como la información necesaria para crear su cuenta y perfil.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String firstname;
    String lastname;
    String password;
    String contact;
    byte[] picture;
    String description;
}
