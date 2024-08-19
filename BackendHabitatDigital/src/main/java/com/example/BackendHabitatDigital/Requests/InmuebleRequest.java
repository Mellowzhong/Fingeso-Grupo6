package com.example.BackendHabitatDigital.Requests;

import lombok.Data;

import java.util.List;

@Data
public class InmuebleRequest {
    //user
    private String email;

    //product
    private Boolean venta;
    private int monto;
    private String direccion;
    private String tipo;
    private int numero_habitaciones;
    private int numero_banos;
    private int metros_cuadrados;
    private int ano_construccion;
    private String estado_propiedad;
    private String descripcion;
    private String servicios_cercanos;
    private Boolean estacionamiento;
    private Boolean amoblado;
    private Boolean aprobacion;
    private List<String> imagenes;
    private String corredorEmail;
}