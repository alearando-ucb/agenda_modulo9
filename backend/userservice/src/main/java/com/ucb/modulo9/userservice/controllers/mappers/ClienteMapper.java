package com.ucb.modulo9.userservice.controllers.mappers;

import com.ucb.modulo9.userservice.controllers.dtos.ClienteRequest;
import com.ucb.modulo9.userservice.controllers.dtos.ClienteResponse;
import com.ucb.modulo9.userservice.entities.Cliente;

public class ClienteMapper {

    public static Cliente toCliente(ClienteRequest clienteRequest) {
        return new Cliente(clienteRequest.getNombre(), clienteRequest.getEmail(), clienteRequest.getUsername(), clienteRequest.getPassword());
    }

    public static ClienteResponse toClienteResponse(Cliente cliente) {
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setId(cliente.getId());
        clienteResponse.setNombre(cliente.getNombre());
        clienteResponse.setEmail(cliente.getEmail());
        clienteResponse.setUsername(cliente.getUsername());
        return clienteResponse;
    }

}
