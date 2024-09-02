package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.Requests.PropertyRequest;
import com.example.BackendHabitatDigital.entities.OwnerEntity;
import com.example.BackendHabitatDigital.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
    Descripcion: Esta clase es un controlador REST que maneja las solicitudes HTTP
    relacionadas con los propietarios (owners). Define los endpoints para obtener,
    crear y listar propietarios en el sistema. Utiliza `OwnerService` para manejar
    la lógica de negocio relacionada con los propietarios.
 */
@RestController
@RequestMapping(path = "/owner")
public class OwnerController {

    private final OwnerService ownerService;

    /*
        Descripcion: Constructor de la clase `OwnerController` que inyecta
        la dependencia `OwnerService` para manejar la lógica de negocio relacionada
        con los propietarios.
     */
    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /*
        Descripcion: Este método obtiene un propietario a partir de su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OwnerEntity> getPropietario(@PathVariable Long id) {
        Optional<OwnerEntity> propietario = ownerService.getOwnerById(id);
        return propietario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
        Descripcion: Este método obtiene todos los propietarios registrados.
     */
    @GetMapping
    public List<OwnerEntity> getAllOwners() {
        return this.ownerService.getAllOwners();
    }

    /*
        Descripcion: Este método crea un nuevo propietario.
     */
    @PostMapping
    public ResponseEntity<Object> createPropietario(@RequestBody PropertyRequest ownerForm) {
        return this.ownerService.addOwner(ownerForm);
    }

}

