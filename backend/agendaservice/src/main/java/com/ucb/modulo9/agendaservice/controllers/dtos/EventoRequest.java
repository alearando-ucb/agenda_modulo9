package com.ucb.modulo9.agendaservice.controllers.dtos;

import java.time.LocalDateTime;

public class EventoRequest {

    private String titulo;
    private String descripcion;
    private LocalDateTime fecha;
    private Long clienteId;
    private Double latitude;
    private Double longitude;

    public EventoRequest() {
    }

    public EventoRequest(String titulo, String descripcion, LocalDateTime fecha, Long clienteId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.clienteId = clienteId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
