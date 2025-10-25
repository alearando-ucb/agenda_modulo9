package com.ucb.modulo9.agendaservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String nombreCompleto;

    protected Cliente() {
    }

    public Cliente(Long id, String nombreCompleto) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombreCompleto == null) ? 0 : nombreCompleto.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombreCompleto == null) {
            if (other.nombreCompleto != null)
                return false;
        } else if (!nombreCompleto.equals(other.nombreCompleto))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return nombreCompleto;
    }

}
