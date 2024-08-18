package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.Requests.InmuebleRequest;
import com.example.BackendHabitatDigital.entities.OwnerEntity;
import com.example.BackendHabitatDigital.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/owner")
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerEntity> getPropietario(@PathVariable Long id) {
        Optional<OwnerEntity> propietario = ownerService.getOwnerById(id);
        return propietario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<OwnerEntity> getAllOwners() {
        return this.ownerService.getAllOwners();
    }

    @PostMapping
    public ResponseEntity<Object> createPropietario(@RequestBody InmuebleRequest ownerForm) {
        return this.ownerService.addOwner(ownerForm);
    }

}

