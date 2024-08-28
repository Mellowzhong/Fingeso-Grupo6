package com.example.BackendHabitatDigital.services;

import com.example.BackendHabitatDigital.entities.ProfileEntity;
import com.example.BackendHabitatDigital.entities.UserEntity;
import com.example.BackendHabitatDigital.repositories.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/*
    Descripcion: Esta clase `ProfileService` es un servicio que gestiona la lógica de negocio relacionada
    con los perfiles de usuario en el sistema. Proporciona métodos para crear, obtener, actualizar y eliminar
    perfiles, así como para manejar la carga y descarga de imágenes de perfil.
 */
@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserService userService;

    /*
        Descripcion: Este método guarda un perfil en el repositorio de perfiles.
     */
    public ProfileEntity saveProfile(ProfileEntity profile) { return profileRepository.save(profile); }

    /*
        Descripcion: Este método obtiene una lista de todos los perfiles registrados en el sistema.
     */
    public List<ProfileEntity> getProfiles() {
        return profileRepository.findAll();
    }

    /*
        Descripcion: Este método obtiene un perfil específico por su ID.
     */
    public Optional<ProfileEntity> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    /*
        Descripcion: Este método actualiza un perfil existente. Verifica que el usuario esté autenticado
        y luego actualiza los campos del perfil con los nuevos valores proporcionados.
     */
    public ProfileEntity updateProfile(ProfileEntity profile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }

        ProfileEntity existingProfile = profileRepository.findById(profile.getId())
                .orElseThrow(() -> new EntityNotFoundException("Profile with id " + profile.getId() + " does not exist."));

//        UserEntity existingUser = userService.getUserByProfileId (existingProfile.getId())
//                .orElseThrow(() -> new EntityNotFoundException("User with profile id " + existingProfile.getId() + " does not exist."));


        if (profile.getFirstname() != null) {
            existingProfile.setFirstname(profile.getFirstname());
        }

        if (profile.getPhoto() != null) {
            existingProfile.setPhoto(profile.getPhoto());
        }

        if (profile.getLastname() != null) {
            existingProfile.setLastname(profile.getLastname());
        }

        if (profile.getContact() != null) {
            existingProfile.setContact(profile.getContact());
        }

        if(profile.getDescription() != null){
            existingProfile.setDescription(profile.getDescription());
        }

        if(profile.getPhoto() != null){
            existingProfile.setPhoto(profile.getPhoto());
        }

        return profileRepository.save(existingProfile);
    }

    /*
       Descripcion: Este método elimina un perfil por su ID. Solo puede ser ejecutado por usuarios con
       rol de `ADMIN`.
     */
   @PreAuthorize("hasRole('ADMIN')")
   public boolean deleteProfile(Long id) throws Exception {
       try {
           profileRepository.deleteById(id);
           return true;
       } catch (Exception e) {
           throw new Exception(e.getMessage());
       }
   }


    public byte[] handleFileUpload(MultipartFile file) throws Exception {
        try {
            String originalFilename = file.getOriginalFilename();

            if (!originalFilename.endsWith(".jpg")
                    && !originalFilename.endsWith("png")
                    && !originalFilename.endsWith("jpeg")) {
                throw new Exception("Only JPG, JPEG & PNG files allowed");
            }

            long fileSize = file.getSize();

            int maxFileSize = 5 * 1024 * 1024;

            if (fileSize > maxFileSize) {
                throw new Exception("File size must be less or equal that 5MB");
            }

            byte[] bytes = file.getBytes();

            System.out.println(bytes);

            return bytes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public byte[] handleImageDownload(String imageUrl) throws Exception {
        ImageInputStream iis = null;
        try {
            URL url = new URL(imageUrl);
            iis = ImageIO.createImageInputStream(url.openStream());
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);

            if (!imageReaders.hasNext()) {
                throw new Exception("No image readers found for the image");
            }

            ImageReader reader = imageReaders.next();
            reader.setInput(iis);
            String formatName = reader.getFormatName();

            System.out.println("FileExtension: " + formatName);

            if (!formatName.equalsIgnoreCase("jpg")
                    && !formatName.equalsIgnoreCase("png")
                    && !formatName.equalsIgnoreCase("jpeg")) {
                throw new Exception("Only JPG, JPEG & PNG files allowed");
            }

            BufferedImage image = reader.read(0);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, formatName, baos);
            byte[] bytes = baos.toByteArray();

            int maxFileSize = 5 * 1024 * 1024; // 5MB
            if (bytes.length > maxFileSize) {
                throw new Exception("File size must be less or equal to 5MB");
            }

            return bytes;
        } catch (IOException e) {
            throw new Exception("Failed to download or process image: " + e.getMessage(), e);
        } finally {
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException ex) {
                    System.err.println("Failed to close ImageInputStream: " + ex.getMessage());
                }
            }
        }
    }
}