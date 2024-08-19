package com.example.BackendHabitatDigital.controllers;

import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/profile")
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @PutMapping("/")
    public ResponseEntity<ProfileEntity> updateProfile(@RequestBody ProfileEntity profile){
        ProfileEntity newProfile = profileService.updateProfile(profile);
        return ResponseEntity.ok(newProfile);
    }

    @GetMapping("/{id}/picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long id) {
        ProfileEntity profile = profileService.getProfileById(id)
                .orElseThrow(() -> new RuntimeException("Profile with id" + id + " does not exist"));

        byte[] imageData = profile.getPicture();
        if (imageData == null || imageData.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(imageData);
    }
}
