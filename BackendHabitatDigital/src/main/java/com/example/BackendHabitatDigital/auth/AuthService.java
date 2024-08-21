package com.example.BackendHabitatDigital.auth;

import com.example.BackendHabitatDigital.Requests.LoginRequest;
import com.example.BackendHabitatDigital.Requests.RegisterRequest;
import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.entities.RoleEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.jwt.JwtService;
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
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /*
        Descripcion: Este método gestiona la operación de login. Autentica al usuario
        utilizando el `AuthenticationManager`, busca los detalles del usuario en el
        repositorio y genera un token JWT para el usuario autenticado.
    */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    /*
        Descripcion: Este método gestiona la operación de registro de un nuevo usuario.
        Valida el nombre de usuario y la contraseña, crea un perfil asociado, guarda
        el usuario en la base de datos, y genera un token JWT para el nuevo usuario.
     */
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

                System.out.println(profile);
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

    /*
        Descripcion: Este método privado valida si la contraseña cumple con los requisitos
        mínimos, en este caso, que tenga al menos 3 caracteres.
    */
    private boolean isAdmittedPassword(String password) {
        return password.length() >= 3;
    }


    /*
        Descripcion: Este método privado verifica si un nombre de usuario ya existe
        en el repositorio. Es utilizado para evitar duplicados en el registro de nuevos usuarios.
     */
    private boolean isAdmittedUsername(String username) {
        return !userRepository.existsByUsername(username);
    }
}
