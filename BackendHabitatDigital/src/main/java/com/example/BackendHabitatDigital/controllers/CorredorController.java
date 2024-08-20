package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.entities.CorredorEntity;
import com.example.BackendHabitatDigital.entities.InmuebleEntity;
import com.example.BackendHabitatDigital.services.CorredorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/corredor")
public class CorredorController {

    private final CorredorService corredorService;

    @Autowired
    public CorredorController(CorredorService corredorService) {
        this.corredorService = corredorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorredorEntity> getCorredor(@PathVariable Long id) {
        Optional<CorredorEntity> corredor = corredorService.getCorredorById(id);
        return corredor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<CorredorEntity> getAllCorredores() {
        return this.corredorService.getAllCorredores();
    }

    @PostMapping
    public ResponseEntity<Object> createCorredor(@RequestBody String email) {
        return this.corredorService.addCorredor(email);
    }
    // Get properties of the authenticated corredor
    @GetMapping("/propiedades")
    public ResponseEntity<List<InmuebleEntity>> findInmueblesByAuthenticatedCorredor() {
        List<InmuebleEntity> inmuebles = corredorService.getInmueblesByAuthenticatedCorredor();
        return ResponseEntity.ok(inmuebles);
    }

    @GetMapping("/explorar")
    public ResponseEntity<List<InmuebleEntity>> findInmueblesWithoutCorredor() {
        List<InmuebleEntity> inmuebles = corredorService.getInmueblesWithoutCorredorForAuthenticatedCorredor();
        return ResponseEntity.ok(inmuebles);
    }

}
