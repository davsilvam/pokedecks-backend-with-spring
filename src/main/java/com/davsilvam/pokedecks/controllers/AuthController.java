package com.davsilvam.pokedecks.controllers;

import com.davsilvam.pokedecks.services.AuthenticationService;
import com.davsilvam.pokedecks.services.dtos.AuthenticateResponseDTO;
import com.davsilvam.pokedecks.services.dtos.CreateUserRequestDTO;
import com.davsilvam.pokedecks.services.dtos.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para registro e autenticação de usuários")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Registra um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "409", description = "Usuário já existe", content = @Content)
    })
    public ResponseEntity<UserResponseDTO> register(@RequestBody CreateUserRequestDTO request) {
        UserResponseDTO response = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Autenticar usuário", description = "Autentica um usuário existente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content)
    })
    public ResponseEntity<AuthenticateResponseDTO> authenticate(Authentication authentication) {
        String token = authenticationService.authenticate(authentication);
        AuthenticateResponseDTO response = new AuthenticateResponseDTO(token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
