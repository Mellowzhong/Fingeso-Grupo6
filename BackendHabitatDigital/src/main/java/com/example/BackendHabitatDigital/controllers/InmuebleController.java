package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.entities.InmuebleEntity;
import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.repositories.InmuebleRepository;
import com.example.BackendHabitatDigital.services.InmuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/add")
    public ResponseEntity<?> createInmueble(@RequestBody InmuebleEntity inmueble) {
        // Authenticate
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verify session
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        // Get email
        String userEmail = authentication.getName();

        // Call to service
        return inmuebleService.addProduct(inmueble, userEmail);
    }

    //Function to delete a product
    @DeleteMapping("/{inmuebleId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long inmuebleId) {
        return this.inmuebleService.deleteInmueble(inmuebleId);
    }

    @GetMapping("/profileO/{inmuebleId}")
    public ProfileEntity getOwnerProfileFromInmueble(@PathVariable Long inmuebleId) {
        return this.inmuebleService.getOwnerProfile(inmuebleId);
    }

    @GetMapping("/testing/{userId}")
    public List<InmuebleEntity> getAllInmueblesByOwner(@PathVariable Long userId) {
        return this.inmuebleService.findAllInmueblesByOwner(userId);
    }

    // Endpoint to get properties without a corredor assigned
    @GetMapping("/sin-corredor")
    public ResponseEntity<List<InmuebleEntity>> getInmueblesWithoutCorredor() {
        List<InmuebleEntity> inmuebles = inmuebleService.findInmueblesWithoutCorredor();
        return ResponseEntity.ok(inmuebles);
    }
}