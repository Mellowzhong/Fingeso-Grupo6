package com.example.BackendHabitatDigital.config;

import com.example.BackendHabitatDigital.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
    Descripcion: Esta clase de configuración define los beans necesarios para la
    autenticación dentro de la aplicación.Es utilizada por Spring Security para manejar la seguridad
    de la aplicación.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

    /*
    escripcion: Este bean proporciona una instancia de `AuthenticationManager`,
    que es el encargado de manejar la autenticación en la aplicación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /*
        Descripcion: Este bean define un `AuthenticationProvider`, en este caso un
        `DaoAuthenticationProvider`, que se utiliza para autenticar a los usuarios
        con un servicio de detalles de usuario personalizado y un codificador de contraseñas.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /*
        Descripcion: Este bean define un `PasswordEncoder` que se utiliza para codificar
        las contraseñas de los usuarios. En este caso, se usa `BCryptPasswordEncoder`
        para realizar la codificación, que es un estándar seguro para almacenar contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

     /*
        Descripcion: Este bean define un `UserDetailsService` personalizado que
        se utiliza para cargar los detalles de un usuario desde la base de datos
        utilizando el `UserRepository`. Busca un usuario por su nombre de usuario
        y lanza una excepción si no se encuentra.
      */
    @Bean
    public UserDetailsService userDetailService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
