package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.entities.InmuebleEntity;
import com.example.BackendHabitatDigital.repositories.InmuebleRepository;
import com.example.BackendHabitatDigital.services.InmuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/inmueble")
public class InmuebleController {
    private final InmuebleService inmuebleService;

    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Autowired
    public InmuebleController(InmuebleService inmuebleService) {
        this.inmuebleService = inmuebleService;
    }

    //Function to add a product
    @GetMapping("/{id}")
    public ResponseEntity<InmuebleEntity> getInmueble(@PathVariable Long id) {
        Optional<InmuebleEntity> inmueble = inmuebleRepository.findById(id);
        return inmueble.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<InmuebleEntity> getAllInmueble() { return this.inmuebleService.getAllInmuebles();}

    @PostMapping("/{userEmail}")
    public ResponseEntity<InmuebleEntity> createInmueble(@RequestBody InmuebleEntity inmueble, @PathVariable String userEmail) {
        return inmuebleService.addProduct(inmueble, userEmail);
    }

    //Function to delete a product
    @DeleteMapping("/{inmuebleId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long inmuebleId) {
        return this.inmuebleService.deleteInmueble(inmuebleId);
    }

}