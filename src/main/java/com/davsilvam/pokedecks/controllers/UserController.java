package com.davsilvam.pokedecks.controllers;

import com.davsilvam.pokedecks.services.OrderService;
import com.davsilvam.pokedecks.services.UserService;
import com.davsilvam.pokedecks.services.dtos.EditUserProfileRequestDTO;
import com.davsilvam.pokedecks.services.dtos.OrderResponseDTO;
import com.davsilvam.pokedecks.services.dtos.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/me")
    @Operation(
            summary = "Obter usuário atual",
            description = "Retorna os detalhes do usuário atualmente autenticado. O usuário é identificado pelo token JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário retornado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado",
                    content = @Content
            )
    })
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserResponseDTO userResponse = userService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obter usuário por ID",
            description = "Retorna os detalhes de um usuário específico pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário retornado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content
            )
    })
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        UserResponseDTO userResponse = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }


    @GetMapping
    @Operation(
            summary = "Obter todos os usuários",
            description = "Retorna uma lista de todos os usuários registrados. Restrito a administradores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é administrador",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @GetMapping("/{userId}/orders")
    @Operation(
            summary = "Obter pedidos por ID do usuário",
            description = "Retorna uma lista de pedidos feitos por um usuário específico pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pedidos retornada com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = OrderResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content
            )
    })
    @Tag(name = "Pedidos")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable UUID userId) {
        List<OrderResponseDTO> response = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Editar perfil do usuário",
            description = "Permite editar o perfil de um usuário específico pelo seu ID. Usuários só podem editar seu próprio perfil."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil do usuário editado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida - dados de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário tentando editar perfil de outro usuário",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content
            )
    })
    public ResponseEntity<UserResponseDTO> editProfile(Authentication authentication, @PathVariable UUID id, @RequestBody EditUserProfileRequestDTO editUserProfileRequestDTO) {
        UserResponseDTO currentUser = userService.findByEmail(authentication.getName());

        if (!currentUser.id().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        UserResponseDTO editedUser = userService.editProfile(id, editUserProfileRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(editedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar usuário por ID",
            description = "Deleta um usuário específico pelo seu ID. Usuários só podem deletar sua própria conta."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário tentando deletar conta de outro usuário",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteUser(Authentication authentication, @PathVariable UUID id) {
        UserResponseDTO currentUser = userService.findByEmail(authentication.getName());

        if (!currentUser.id().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
