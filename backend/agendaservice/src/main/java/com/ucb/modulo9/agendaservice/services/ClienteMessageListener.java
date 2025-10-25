package com.ucb.modulo9.agendaservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.ucb.modulo9.agendaservice.config.RabbitMQConfig;
import com.ucb.modulo9.agendaservice.entities.Cliente;
import com.ucb.modulo9.agendaservice.repositories.ClienteRepository;
import com.ucb.modulo9.agendaservice.services.dtos.ClienteCreatedDTO;

@Service
public class ClienteMessageListener {

    private static final Logger log = LoggerFactory.getLogger(ClienteMessageListener.class);
    private final ClienteRepository clienteRepository;

    public ClienteMessageListener(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleClienteCreated(ClienteCreatedDTO dto) {
        log.info("Mensaje recibido de RabbitMQ: Creando cliente con ID {}", dto.getId());

        try {
            Cliente cliente = new Cliente(dto.getId(), dto.getNombre());
            clienteRepository.save(cliente);
            log.info("Cliente con ID {} guardado exitosamente en agendaservice.", dto.getId());
        } catch (Exception e) {
            log.error("Error al guardar el cliente con ID {}: {}", dto.getId(), e.getMessage());
            // Aquí se podría implementar una lógica de reintento o enviar a una cola de errores.
        }
    }
}
