package com.example.BackendHabitatDigital.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Descripcion: Esta clase `LoginRequest` se utiliza para encapsular la información necesaria
    para realizar una solicitud de inicio de sesión en el sistema. Contiene el nombre de usuario
    y la contraseña, que son necesarios para autenticar al usuario.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    String username;
    String password;
}
