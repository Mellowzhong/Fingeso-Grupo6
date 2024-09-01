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

/*
    Descripcion: Esta clase es un controlador REST que maneja las solicitudes HTTP
    relacionadas con los inmuebles. Define los endpoints para obtener, crear,
    eliminar y gestionar propiedades, así como para interactuar con los perfiles
    de los propietarios y corredores. Utiliza `InmuebleService` y `InmuebleRepository`
    para manejar la lógica de negocio y las interacciones con la base de datos.
 */
@RestController
@RequestMapping(path = "/inmueble")
public class InmuebleController {
    private final InmuebleService inmuebleService;

    @Autowired
    private InmuebleRepository inmuebleRepository;

    /*
        Descripcion: Constructor de la clase `InmuebleController` que inyecta
        las dependencias necesarias, incluyendo `InmuebleService` para manejar
        la lógica de negocio.
     */
    @Autowired
    public InmuebleController(InmuebleService inmuebleService) {
        this.inmuebleService = inmuebleService;
    }


    /*
        Descripcion: Este método maneja las solicitudes GET para obtener la información
        de un inmueble específico por su ID. Si se encuentra, devuelve la entidad
        del inmueble; de lo contrario, devuelve una respuesta 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<InmuebleEntity> getInmueble(@PathVariable Long id) {
        Optional<InmuebleEntity> inmueble = inmuebleRepository.findById(id);
        return inmueble.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    /*
        Descripcion: Este método maneja las solicitudes GET para obtener una lista
        de todos los inmuebles registrados en el sistema.
     */
    @GetMapping
    public List<InmuebleEntity> getAllInmueble() { return this.inmuebleService.getAllInmuebles();}

    /*
        Descripcion: Este método maneja las solicitudes GET para obtener una lista
        de todos los inmuebles registrados en el sistema que esten disponibles.
     */
    @GetMapping("/allAvailable")
    public List<InmuebleEntity> getAllInmuebleAvailable() { return this.inmuebleService.getAllinmeblesDisponibles();}

    /*
        Descripcion: Este método maneja las solicitudes POST para crear un nuevo
        inmueble en el sistema. Verifica que el usuario esté autenticado antes
        de permitir la creación del inmueble.
     */
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
        return inmuebleService.addProperty(inmueble, userEmail);
    }

    @PutMapping
    public ResponseEntity<InmuebleEntity> updateProfile(@RequestBody InmuebleEntity inmueble){
        InmuebleEntity newInmueble = inmuebleService.updateInmueble(inmueble);
        return ResponseEntity.ok(newInmueble);
    }

    /*
        Descripcion: metodo que elimina un mueble
     */
    @DeleteMapping("/{inmuebleId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long inmuebleId) {
        return this.inmuebleService.deleteInmueble(inmuebleId);
    }

    /*
        Descripcion: metodo que obtiene un inmueble a partir de un propietario
     */
    @GetMapping("/profileO/{inmuebleId}")
    public ProfileEntity getOwnerProfileFromInmueble(@PathVariable Long inmuebleId) {
        return this.inmuebleService.getOwnerProfile(inmuebleId);
    }

    /*
        Descripcion: metodo que obtiene todos los inmuebles a partir de un propietario
     */
    @GetMapping("/testing/{userId}")
    public List<InmuebleEntity> getAllInmueblesByOwner(@PathVariable Long userId) {
        return this.inmuebleService.findAllInmueblesByOwner(userId);
    }

    /*
        Descripcion: metodo que obtiene todos los inmuebles que no tienen un corredor asociado
     */
    /*
    Descripcion: Este endpoint permite a un propietario asignar un corredor a un inmueble.
    Requiere los IDs del inmueble y del corredor.
    URL: /inmueble/{inmuebleId}/asigncorredor/{corredorId}
 */
    @PostMapping("/{inmuebleId}/requestcorredor/{corredorId}")
    public ResponseEntity<String> requestCorredorToInmueble(
            @PathVariable Long inmuebleId,
            @PathVariable Long corredorId) {

        return inmuebleService.requestCorredorToInmueble(inmuebleId, corredorId);
    }

}