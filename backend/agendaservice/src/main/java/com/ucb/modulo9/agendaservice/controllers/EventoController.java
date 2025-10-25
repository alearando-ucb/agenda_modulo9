package com.ucb.modulo9.agendaservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucb.modulo9.agendaservice.controllers.dtos.EventoRequest;
import com.ucb.modulo9.agendaservice.controllers.dtos.EventoResponse;
import com.ucb.modulo9.agendaservice.controllers.mappers.EventoMapper;
import com.ucb.modulo9.agendaservice.entities.Evento;
import com.ucb.modulo9.agendaservice.exceptions.CustomValidationException;
import com.ucb.modulo9.agendaservice.services.EventoService;

@RestController
@RequestMapping("api/v1/eventos")
public class EventoController {

    private EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> crearEvento(@RequestBody EventoRequest eventoRequest) {

        try{
            Evento evento = eventoService.crearEvento(EventoMapper.toEvento(eventoRequest), eventoRequest.getClienteId());
            return new ResponseEntity<>(EventoMapper.toEventoResponse(evento), HttpStatus.CREATED);
        }catch(CustomValidationException e){
            return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<?> getEventosByCliente(@PathVariable Long clienteId) {
        try {
            List<Evento> eventos = eventoService.getEventoByCliente(clienteId);
            List<EventoResponse> eventoResponses = eventos.stream()
                    .map(EventoMapper::toEventoResponse)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(eventoResponses, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
