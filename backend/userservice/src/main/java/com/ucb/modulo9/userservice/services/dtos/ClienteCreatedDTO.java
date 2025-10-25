package com.ucb.modulo9.userservice.services.dtos;

import com.ucb.modulo9.userservice.entities.Cliente;

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

    public void fromCliente(Cliente cliente) {
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

}
