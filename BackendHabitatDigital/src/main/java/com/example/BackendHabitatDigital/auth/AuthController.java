package com.example.BackendHabitatDigital.auth;

import com.example.BackendHabitatDigital.Requests.LoginRequest;
import com.example.BackendHabitatDigital.Requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/*
    Descripcion: Esta clase esta encargada de realizar los mapeos para todo lo que esta
    encargado de la seguridad en la autenticacion del sistema.
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /*
    Descripcion: Este metodo es el encargado de mapear el login al servicio de autenticacion
 */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    /*
        Descripcion: Este método está encargado de mapear la operación de registro
        de usuarios.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) throws Exception {
        return ResponseEntity.ok(authService.register(request));
    }
}
