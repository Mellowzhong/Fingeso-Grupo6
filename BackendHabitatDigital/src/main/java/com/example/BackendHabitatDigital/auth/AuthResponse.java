package com.example.BackendHabitatDigital.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Descripcion: Esta clase representa la respuesta que se envía al cliente después
    de un proceso de autenticación exitoso. Contiene el token de autenticación que permite al cliente
    realizar solicitudes autenticadas en el sistema.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    String token;
}
