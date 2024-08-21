package com.example.BackendHabitatDigital.services;

import com.example.BackendHabitatDigital.entities.OwnerEntity;
import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.entities.RoleEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.jwt.JwtAuthenticationFilter;
import com.example.BackendHabitatDigital.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/*
    Descripcion: Esta clase `UserService` es un servicio que gestiona la lógica de negocio relacionada
    con los usuarios en el sistema. Proporciona métodos para crear, obtener, actualizar y eliminar usuarios,
    así como para gestionar roles y contraseñas de usuarios.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    private final PasswordEncoder passwordEncoder;

    /*
        Descripcion: Este método crea un nuevo usuario en el sistema y lo guarda en el repositorio de usuarios.
     */
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    /*
        Descripcion: Este método retorna una lista de todos los usuarios registrados en el sistema.
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /*
        Descripcion: Este método busca un usuario específico por el ID de su perfil.
     */
    public Optional<UserEntity> getUserByProfileId(Long id) {
        return userRepository.findByProfileId(id);
    }

    /*
        Descripcion: Este método busca un usuario específico por su nombre de usuario.
     */
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /*
        Descripcion: Este método busca un usuario específico por su ID.
     */
    public Boolean userExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /*
        Descripcion: Este método actualiza la información de un usuario existente, asegurando que solo un usuario
        autenticado con rol de administrador pueda realizar cambios en el nombre de usuario o la contraseña.
     */
    public UserEntity updateUser(UserEntity updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }

        String currentUsername = authentication.getName();

        UserEntity existingUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + currentUsername + " not found"));

        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));

        if (!isAdmin) {
            throw new SecurityException("Not authorized to update this user");
        }

        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    /*
        Descripcion: Este método actualiza la contraseña de un usuario existente.
     */
    public UserEntity updatePassword(UserEntity user) {
        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + user.getId() + " does not exist."));

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    /*
        Descripcion: Este método actualiza el rol de un usuario. Solo los usuarios con rol de administrador
        pueden realizar esta operación.
     */
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity updateUserRole(UserEntity user) throws Exception {
        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + user.getId() + " does not exist."));

        if (user.getRole() != null) {
            if (!Objects.equals(user.getRole().toString(), "ADMIN")) {
                RoleEntity role = RoleEntity.fromString(user.getRole().toString());
                existingUser.setRole(role);
                return userRepository.save(existingUser);
            }
        }

        throw new Exception("Try a different role.");
    }

    /*
        Descripcion: Este método elimina un usuario por su ID. Solo los usuarios con rol de administrador
        pueden realizar esta operación.
     */
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteUser(Long id) throws Exception {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}