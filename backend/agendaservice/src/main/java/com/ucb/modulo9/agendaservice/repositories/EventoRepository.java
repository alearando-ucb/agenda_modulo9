package com.ucb.modulo9.agendaservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucb.modulo9.agendaservice.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    public List<Evento> findAllByClienteId(Long clienteId);
}
