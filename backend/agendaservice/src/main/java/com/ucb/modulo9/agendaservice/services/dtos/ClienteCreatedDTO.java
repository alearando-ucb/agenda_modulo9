package com.ucb.modulo9.agendaservice.services.dtos;

import java.io.Serializable;

public class ClienteCreatedDTO implements Serializable {

    private Long id;
    private String nombre;

    public ClienteCreatedDTO() {
    }

    public ClienteCreatedDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
