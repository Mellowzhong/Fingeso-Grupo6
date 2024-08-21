package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/*
    Descripcion: Esta clase es un controlador REST que maneja las solicitudes HTTP
    relacionadas con la actualización de perfiles de usuario. Define el endpoint
    para actualizar un perfil existente en el sistema. Utiliza `ProfileService`
    para manejar la lógica de negocio relacionada con los perfiles.
 */
@RestController
@RequestMapping(path = "/profile")
public class ProfileController {
    @Autowired
    ProfileService profileService;

    /*
    Descripcion: Este método esta encargado de mapear la solitud para modificar un perfil mediante su servicio asociado
 */
    @PutMapping("/")
    public ResponseEntity<ProfileEntity> updateProfile(@RequestBody ProfileEntity profile){
        ProfileEntity newProfile = profileService.updateProfile(profile);
        return ResponseEntity.ok(newProfile);
    }
}