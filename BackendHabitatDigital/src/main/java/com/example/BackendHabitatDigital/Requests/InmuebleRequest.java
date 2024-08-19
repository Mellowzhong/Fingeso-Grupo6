package com.example.BackendHabitatDigital.Requests;

import lombok.Data;

import java.util.List;

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