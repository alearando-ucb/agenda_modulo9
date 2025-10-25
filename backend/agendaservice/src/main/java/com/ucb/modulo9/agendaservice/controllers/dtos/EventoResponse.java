package com.ucb.modulo9.agendaservice.controllers.dtos;

import java.time.LocalDateTime;

public class EventoResponse {

    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha;
    private Long clienteId;

    public EventoResponse() {
    }

    public EventoResponse(Long id, String titulo, String descripcion, LocalDateTime fecha, Long clienteId) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.clienteId = clienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
