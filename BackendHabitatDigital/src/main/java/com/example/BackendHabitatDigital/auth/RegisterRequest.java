package com.example.BackendHabitatDigital.auth;

import com.example.BackendHabitatDigital.entities.RoleEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String firstname;
    String lastname;
    String password;
    String email;
    Integer contact;
}
