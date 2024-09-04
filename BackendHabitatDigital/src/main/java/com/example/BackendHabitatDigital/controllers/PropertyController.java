package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.entities.PropertyEntity;
import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.repositories.PropertyRepository;
import com.example.BackendHabitatDigital.services.PropertyService;
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
    de los propietarios y corredores. Utiliza `PropertyService` y `PropertyRepository`
    para manejar la lógica de negocio y las interacciones con la base de datos.
 */
@RestController
@RequestMapping(path = "/inmueble")
public class PropertyController {
    private final PropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepository;

    /*
        Descripcion: Constructor de la clase `PropertyController` que inyecta
        las dependencias necesarias, incluyendo `PropertyService` para manejar
        la lógica de negocio.
     */
    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    /*
        Descripcion: Este método maneja las solicitudes GET para obtener la información
        de un inmueble específico por su ID. Si se encuentra, devuelve la entidad
        del inmueble; de lo contrario, devuelve una respuesta 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<PropertyEntity> getInmueble(@PathVariable Long id) {
        Optional<PropertyEntity> inmueble = propertyRepository.findById(id);
        return inmueble.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    /*
        Descripcion: Este método maneja las solicitudes GET para obtener una lista
        de todos los inmuebles registrados en el sistema.
     */
    @GetMapping
    public List<PropertyEntity> getAllInmueble() { return this.propertyService.getAllInmuebles();}

    /*
        Descripcion: Este método maneja las solicitudes GET para obtener una lista
        de todos los inmuebles registrados en el sistema que esten disponibles.
     */
    @GetMapping("/allAvailable")
    public List<PropertyEntity> getAllInmuebleAvailable() { return this.propertyService.getAllinmeblesDisponibles();}

    /*
        Descripcion: Este método maneja las solicitudes POST para crear un nuevo
        inmueble en el sistema. Verifica que el usuario esté autenticado antes
        de permitir la creación del inmueble.
     */
    @PostMapping("/add")
    public ResponseEntity<?> createInmueble(@RequestBody PropertyEntity inmueble) {
        // Authenticate
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verify session
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        // Get email
        String userEmail = authentication.getName();

        // Call to service
        return propertyService.addProperty(inmueble, userEmail);
    }

    @PutMapping
    public ResponseEntity<PropertyEntity> updateProfile(@RequestBody PropertyEntity inmueble){
        PropertyEntity newInmueble = propertyService.updateInmueble(inmueble);
        return ResponseEntity.ok(newInmueble);
    }

    /*
        Descripcion: metodo que elimina un mueble
     */
    @DeleteMapping("/{inmuebleId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long inmuebleId) {
        return this.propertyService.deleteInmueble(inmuebleId);
    }

    /*
        Descripcion: metodo que obtiene un inmueble a partir de un propietario
     */
    @GetMapping("/profileO/{inmuebleId}")
    public ProfileEntity getOwnerProfileFromInmueble(@PathVariable Long inmuebleId) {
        return this.propertyService.getOwnerProfile(inmuebleId);
    }

    /*
        Descripcion: metodo que obtiene todos los inmuebles a partir de un propietario
     */
    @GetMapping("/testing/{userId}")
    public List<PropertyEntity> getAllInmueblesByOwner(@PathVariable Long userId) {
        return this.propertyService.findAllInmueblesByOwner(userId);
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

        return propertyService.requestCorredorToInmueble(inmuebleId, corredorId);
    }

}