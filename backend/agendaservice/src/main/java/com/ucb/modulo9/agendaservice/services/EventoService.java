package com.ucb.modulo9.agendaservice.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ucb.modulo9.agendaservice.entities.Cliente;
import com.ucb.modulo9.agendaservice.entities.Evento;
import com.ucb.modulo9.agendaservice.repositories.ClienteRepository;
import com.ucb.modulo9.agendaservice.repositories.EventoRepository;

@Service
public class EventoService {

    private EventoRepository eventoRepository;
    private ClienteRepository clienteRepository;
    private ValidationService validationService = new ValidationService();

    public EventoService(EventoRepository eventoRepository, ClienteRepository clienteRepository, ValidationService validationService) {
        this.eventoRepository = eventoRepository;
        this.clienteRepository = clienteRepository;
        this.validationService = validationService;
    }

    public Evento crearEvento(Evento evento, Long userId) {

        Map<String, List<String>> errors = new HashMap<>();

        Optional<Cliente> clienteOpt = clienteRepository.findById(userId);
        if (clienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + userId);
        }

        if(!validationService.isStringNotBlank(evento.getTitulo())){
            errors.put("titulo", List.of("El título no puede estar vacío."));
        } else if(!validationService.isStringLengthBetween(evento.getTitulo(), 5, 100)){
            errors.put("titulo", List.of("El título debe tener entre 5 y 100 caracteres."));
        }

        if(!validationService.isStringNotBlank(evento.getDescripcion())){
            errors.put("descripcion", List.of("La descripción no puede estar vacía."));
        } else if(!validationService.isStringLengthBetween(evento.getDescripcion(), 10, 500)){
            errors.put("descripcion", List.of("La descripción debe tener entre 10 y 500 caracteres."));
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Errores de validación: " + errors.toString());
        }

        evento.setCliente(clienteOpt.get());
        return eventoRepository.save(evento);
    }

}
