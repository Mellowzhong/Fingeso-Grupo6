package com.example.BackendHabitatDigital.auth;

import com.example.BackendHabitatDigital.Requests.LoginRequest;
import com.example.BackendHabitatDigital.Requests.RegisterRequest;
import com.example.BackendHabitatDigital.Requests.RegisterCorredorRequest; // Importar la clase para la solicitud de registro de corredor
import com.example.BackendHabitatDigital.entities.CorredorEntity;
import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.entities.RoleEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.jwt.JwtService;
import com.example.BackendHabitatDigital.repositories.CorredorRepository; // Importar el repositorio de corredor
import com.example.BackendHabitatDigital.repositories.ProfileRepository;
import com.example.BackendHabitatDigital.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
    Descripcion: Esta clase se encarga de manejar la lógica de negocio relacionada
    con la autenticación de usuarios, incluyendo las operaciones de login y registro.
    Utiliza repositorios para interactuar con la base de datos, servicios para
    manejar tokens JWT y autenticación, y un codificador de contraseñas.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final CorredorRepository corredorRepository; // Repositorio para CorredorEntity
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) throws Exception {
        if (isAdmittedPassword(request.getPassword())) {
            if (isAdmittedUsername(request.getUsername())) {
                ProfileEntity profile = ProfileEntity.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .contact(request.getContact())
                        .description(request.getDescription())
                        .photo("https://images.unsplash.com/photo-1544502062-f82887f03d1c?q=80&w=3359&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                        .build();

                ProfileEntity savedProfile = profileRepository.save(profile);

                UserEntity user = UserEntity.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(RoleEntity.COMUN)
                        .profile(savedProfile)
                        .build();
                userRepository.save(user);

                return AuthResponse.builder()
                        .token(jwtService.getToken(user))
                        .build();
            } else {
                throw new Exception("Try with another username");
            }
        } else {
            throw new Exception("Try with another password");
        }
    }

    // Método adicional para registrar un usuario tipo corredor
    public AuthResponse registerCorredor(RegisterCorredorRequest request) throws Exception {
        if (isAdmittedPassword(request.getPassword())) {
            if (isAdmittedUsername(request.getUsername())) {
                ProfileEntity profile = ProfileEntity.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .contact(request.getContact())
                        .description(request.getDescription())
                        .photo("https://images.unsplash.com/photo-1544502062-f82887f03d1c?q=80&w=3359&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                        .build();

                ProfileEntity savedProfile = profileRepository.save(profile);

                UserEntity user = UserEntity.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(RoleEntity.CORREDOR) // Rol específico para corredor
                        .profile(savedProfile)
                        .build();

                UserEntity savedUser = userRepository.save(user);

                // Crear una nueva instancia de CorredorEntity asociada al usuario creado
                CorredorEntity corredor = CorredorEntity.builder()
                        .valoracion(request.getValoracion())
                        .user(savedUser)
                        .build();

                corredorRepository.save(corredor);

                return AuthResponse.builder()
                        .token(jwtService.getToken(savedUser))
                        .build();
            } else {
                throw new Exception("Try with another username");
            }
        } else {
            throw new Exception("Try with another password");
        }
    }

    private boolean isAdmittedPassword(String password) {
        return password.length() >= 3;
    }

    private boolean isAdmittedUsername(String username) {
        return !userRepository.existsByUsername(username);
    }
}
