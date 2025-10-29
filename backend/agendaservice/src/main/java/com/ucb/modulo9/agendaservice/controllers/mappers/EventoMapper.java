package com.ucb.modulo9.agendaservice.controllers.mappers;

import com.ucb.modulo9.agendaservice.controllers.dtos.EventoRequest;
import com.ucb.modulo9.agendaservice.controllers.dtos.EventoResponse;
import com.ucb.modulo9.agendaservice.entities.Evento;

public class EventoMapper {

    public static Evento toEvento(EventoRequest eventoRequest) {
        Evento evento = new Evento(eventoRequest.getTitulo(), eventoRequest.getDescripcion(), eventoRequest.getFecha());
        evento.setLatitude(eventoRequest.getLatitude());
        evento.setLongitude(eventoRequest.getLongitude());
        return evento;
    }

    public static EventoResponse toEventoResponse(Evento evento) {
        EventoResponse eventoResponse = new EventoResponse(
            evento.getId(),
            evento.getTitulo(),
            evento.getDescripcion(),
            evento.getFechaHora(),
            evento.getCliente().getId()
        );
        eventoResponse.setLatitude(evento.getLatitude());
        eventoResponse.setLongitude(evento.getLongitude());
        return eventoResponse;
    }
}
