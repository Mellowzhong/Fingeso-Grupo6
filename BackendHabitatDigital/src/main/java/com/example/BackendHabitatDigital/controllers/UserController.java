package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*
    Descripcion: Esta clase es un controlador REST que maneja las solicitudes HTTP
    relacionadas con la gestión de usuarios en el sistema. Define los endpoints
    para crear, obtener y actualizar la información de los usuarios. Utiliza
    `UserService` para manejar la lógica de negocio relacionada con los usuarios.
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

     /*
        Descripcion: Constructor de la clase `UserController` que inyecta
        la dependencia `UserService` para manejar la lógica de negocio relacionada
        con los usuarios.
      */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
        Descripcion: Este metodo esta encargado de crear un usuario.
     */
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    /*
        Descripcion: Este metodo esta encargado de obtener un usuario a partir de su email.
     */
    @GetMapping("/get/{userEmail}")
    public UserEntity getUser(@PathVariable String userEmail) {
        return userService.getUserByUsername(userEmail);
    }

    /*
        Descripcion: Este metodo esta encargado de modificar un usuario.
     */
    @PutMapping("/update")
    public UserEntity updateUser(@RequestBody UserEntity user) {
        return userService.updateUser(user);
    }
}

