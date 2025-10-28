package com.ucb.modulo9.userservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ucb.modulo9.userservice.controllers.dtos.ClienteRequest;
import com.ucb.modulo9.userservice.controllers.dtos.LoginRequest;
import com.ucb.modulo9.userservice.controllers.mappers.ClienteMapper;
import com.ucb.modulo9.userservice.entities.Cliente;
import com.ucb.modulo9.userservice.exceptions.CustomValidationException;
import com.ucb.modulo9.userservice.services.ClienteService;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @PostMapping({"/create"})
    public ResponseEntity<?> createCliente(@RequestBody ClienteRequest clienteRequest) {
        Cliente cliente = ClienteMapper.toCliente(clienteRequest);
        try{
            Cliente createdCliente = clienteService.createCliente(cliente);
            return new ResponseEntity<>(ClienteMapper.toClienteResponse(createdCliente), HttpStatus.CREATED);
        }catch(CustomValidationException e){
            return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try{
            Cliente cliente = clienteService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return new ResponseEntity<>(ClienteMapper.toClienteResponse(cliente), HttpStatus.OK);
        }catch(IllegalArgumentException e){
            java.util.Map<String, String> errorResponse = new java.util.HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/uploadAvatar/{clientId}")
    public ResponseEntity<?> uploadAvatar(@PathVariable Long clientId, @RequestParam("file") MultipartFile file) {
        try {
            String avatarUrl = clienteService.uploadAvatar(clientId, file);
            java.util.Map<String, String> response = new java.util.HashMap<>();
            response.put("message", "Avatar uploaded successfully");
            response.put("avatarUrl", avatarUrl);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            java.util.Map<String, String> errorResponse = new java.util.HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            java.util.Map<String, String> errorResponse = new java.util.HashMap<>();
            errorResponse.put("error", "Failed to upload avatar: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
