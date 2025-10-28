package com.ucb.modulo9.userservice.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ucb.modulo9.userservice.config.RabbitMQConfig;
import com.ucb.modulo9.userservice.entities.Cliente;
import com.ucb.modulo9.userservice.exceptions.CustomValidationException;
import com.ucb.modulo9.userservice.repositories.ClienteRepository;
import com.ucb.modulo9.userservice.services.dtos.ClienteCreatedDTO;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.avatar.upload-dir}")
    private String uploadDir;

    public ClienteService(ClienteRepository clienteRepository, ValidationService validationService, PasswordEncoder passwordEncoder, RabbitTemplate rabbitTemplate) {
        this.clienteRepository = clienteRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
        this.rabbitTemplate = rabbitTemplate;
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
        Cliente savedCliente = clienteRepository.save(cliente);

        // Enviar evento a RabbitMQ
        ClienteCreatedDTO dto = new ClienteCreatedDTO();
        dto.fromCliente(savedCliente);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, dto);

        return savedCliente;
    }

    public Cliente login(String username, String password) {
        Optional<Cliente> clienteOptional = clienteRepository.findByUsername(username);
        if(clienteOptional.isEmpty()){
            throw new IllegalArgumentException("Usuario o contraseña incorrecto");
        }

        if (!passwordEncoder.matches(password, clienteOptional.get().getPassword())) {
            throw new IllegalArgumentException("Usuario o contraseña incorrecto");
        }

        return clienteOptional.get();
    }

    public String uploadAvatar(Long clientId, MultipartFile file) throws IOException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(clientId);
        if (clienteOptional.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        Cliente cliente = clienteOptional.get();

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + fileExtension;

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        // Save the file
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        // Update client with avatar URL
        String avatarUrl = "/avatars/" + filename; // This will be the URL to access the avatar
        cliente.setAvatarUrl(avatarUrl);
        clienteRepository.save(cliente);

        return avatarUrl;
    }

}
