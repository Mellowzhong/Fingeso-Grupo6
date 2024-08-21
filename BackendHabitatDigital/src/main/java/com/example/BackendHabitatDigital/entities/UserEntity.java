package com.example.BackendHabitatDigital.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/*
    Descripcion: Esta clase representa la entidad `UserEntity` que se almacena en la base de datos
    en la tabla `usuario`. La entidad `UserEntity` implementa la interfaz `UserDetails` de Spring Security,
    lo que permite que los usuarios se autentiquen y manejen roles dentro del sistema.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class UserEntity implements UserDetails {
    /*
        Descripcion: Campo que representa el identificador único de la entidad `UserEntity`.
        Es la clave primaria de la tabla `usuario` en la base de datos. El valor es generado
        automáticamente mediante la estrategia `GenerationType.IDENTITY`.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Descripcion: Campo que almacena el nombre de usuario, utilizado para la autenticación.
        Es obligatorio y único dentro del sistema.
     */
    @Column(nullable = false)
    private String username;

    /*
        Descripcion: Relación uno a uno con la entidad `ProfileEntity`, representando que cada
        usuario tiene un perfil asociado. La columna `profileId` en la tabla `usuario` se utiliza
        como clave externa para referenciar la entidad `ProfileEntity`.
     */
    @OneToOne
    @JoinColumn(name = "profileId")
    private ProfileEntity profile;

    /*
        Descripcion: Campo que almacena la contraseña del usuario. Es obligatorio (`nullable = false`)
        y se utiliza para la autenticación.
     */
    @Column(nullable = false)
    private String password;

    /*
        Descripcion: Campo que almacena el rol del usuario en el sistema. Utiliza una enumeración
        `RoleEntity` para definir el rol, que puede ser `ADMIN`, `EXTERNAL`, `COMUN`, `CORREDOR`, o `OWNER`.
        Es obligatorio (`nullable = false`) y se almacena como una cadena de texto (`EnumType.STRING`).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEntity role;

    /*
        Descripcion: Método que devuelve las autoridades otorgadas al usuario según su rol.
        Implementa el método `getAuthorities()` de la interfaz `UserDetails` de Spring Security.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role != null) {
            return List.of(new SimpleGrantedAuthority(role.name()));
        }
        return List.of(new SimpleGrantedAuthority("EXTERNAL"));
    }

    /*
        Descripcion: Método que indica si la cuenta del usuario no ha expirado. Siempre devuelve `true`
        en esta implementación.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
        Descripcion: Método que indica si la cuenta del usuario no está bloqueada. Siempre devuelve `true`
        en esta implementación.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
        Descripcion: Método que indica si las credenciales del usuario no han expirado. Siempre devuelve `true`
        en esta implementación.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
        Descripcion: Método que indica si la cuenta del usuario está habilitada. Siempre devuelve `true`
        en esta implementación.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
