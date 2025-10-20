package com.ucb.modulo9.userservice.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ucb.modulo9.userservice.entities.Cliente;
import com.ucb.modulo9.userservice.exceptions.CustomValidationException;
import com.ucb.modulo9.userservice.repositories.ClienteRepository;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;
    private ValidationService validationService;
    private PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository clienteRepository, ValidationService validationService, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }

    public Cliente createCliente(Cliente cliente) {

        Optional<Cliente> clienteOptional;
        Map<String, List<String>> errors = new HashMap<>();

        if(!validationService.isStringNotBlank(cliente.getNombre())){
            errors.put("nombre", List.of("El nombre no puede estar vacío."));
        } else if(!validationService.isStringLengthBetween(cliente.getNombre(), 5, 50)){
            errors.put("nombre", List.of("El nombre debe tener entre 5 y 50 caracteres."));
        }

        if(!validationService.isStringNotBlank(cliente.getUsername())){
            errors.put("username", List.of("El nombre de usuario no puede estar vacío."));
        } else if(!validationService.isStringLengthBetween(cliente.getUsername(), 5, 15)){
            errors.put("username", List.of("El nombre de usuario debe tener entre 5 y 15 caracteres."));
        } else{
            clienteOptional = clienteRepository.findByUsername(cliente.getUsername());
            if (clienteOptional.isPresent()) {
                errors.put("username", List.of("El nombre de usuario ya está en uso."));
            }
        }

        if(!validationService.isStringNotBlank(cliente.getEmail())){
            errors.put("email", List.of("El email no puede estar vacío."));
        }else if(!validationService.isEmailValid(cliente.getEmail())){
            errors.put("email", List.of("El email no tiene un formato válido."));
        } else{
            clienteOptional = clienteRepository.findByEmail(cliente.getEmail());
            if (clienteOptional.isPresent()) {
                errors.put("email", List.of("El email ya está en uso."));
            }
        }

        if(!validationService.isStringNotBlank(cliente.getPassword())){
            errors.put("password", List.of("La contraseña no puede estar vacía."));
        } else if(!validationService.isPasswordStrong(cliente.getPassword())){
            errors.put("password", List.of("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un caracter especial."));
        }

        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }

        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        return clienteRepository.save(cliente);
    }

}
