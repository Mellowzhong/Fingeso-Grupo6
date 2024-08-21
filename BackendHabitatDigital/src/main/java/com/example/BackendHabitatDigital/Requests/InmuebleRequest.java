package com.example.BackendHabitatDigital.Requests;

import lombok.Data;

import java.util.List;

/*
    Descripcion: Esta clase `InmuebleRequest` se utiliza para encapsular la información necesaria
    para crear o actualizar un inmueble en el sistema. Contiene detalles tanto del usuario asociado
    como de las características del inmueble. Esta clase actúa como un objeto de transferencia de datos
    (DTO) que facilita la comunicación entre el frontend y el backend.
 */
@Data
public class InmuebleRequest {
    //user
    private String userEmail;

    //product
    private Boolean sale;
    private int price;
    private String direction;
    private String type;
    private int rooms;
    private int bathrooms;
    private int squareMeters;
    private int yearConstruction;
    private String state;
    private String description;
    private String services;
    private Boolean parking;
    private Boolean furnished;
    private Boolean aprobated;
    private List<String> photos;
    private String corredorEmail;
}