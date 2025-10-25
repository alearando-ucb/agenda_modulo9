package com.ucb.modulo9.agendaservice.controllers.mappers;

import com.ucb.modulo9.agendaservice.controllers.dtos.EventoRequest;
import com.ucb.modulo9.agendaservice.controllers.dtos.EventoResponse;
import com.ucb.modulo9.agendaservice.entities.Evento;

public class EventoMapper {

    public static Evento toEvento(EventoRequest eventoRequest) {
        Evento evento = new Evento(eventoRequest.getTitulo(), eventoRequest.getDescripcion(), eventoRequest.getFecha());
        return evento;
    }

    public static EventoResponse toEventoResponse(Evento evento) {
        return new EventoResponse(
            evento.getId(),
            evento.getTitulo(),
            evento.getDescripcion(),
            evento.getFechaHora(),
            evento.getCliente().getId()
        );
    }
}
