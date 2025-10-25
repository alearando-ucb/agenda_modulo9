package com.ucb.modulo9.agendaservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucb.modulo9.agendaservice.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
