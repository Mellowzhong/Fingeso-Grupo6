package com.example.BackendHabitatDigital.auth;

import com.example.BackendHabitatDigital.Requests.LoginRequest;
import com.example.BackendHabitatDigital.Requests.RegisterRequest;
import com.example.BackendHabitatDigital.Requests.RegisterCorredorRequest; // Importar la nueva clase de solicitud
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    Descripcion: Esta clase está encargada de realizar los mapeos para todo lo que está
    encargado de la seguridad en la autenticación del sistema.
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /*
        Descripcion: Este método es el encargado de mapear el login al servicio de autenticación.
    */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /*
        Descripcion: Este método está encargado de mapear la operación de registro de usuarios.
    */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) throws Exception {
        return ResponseEntity.ok(authService.register(request));
    }

    /*
        Descripcion: Este método está encargado de mapear la operación de registro de usuarios corredores.
    */
    @PostMapping("/register/corredor")
    public ResponseEntity<AuthResponse> registerCorredor(@RequestBody RegisterCorredorRequest request) throws Exception {
        return ResponseEntity.ok(authService.registerCorredor(request)); // Llamar al nuevo método de servicio
    }
}
