package com.davsilvam.pokedecks.controllers;

import com.davsilvam.pokedecks.services.OrderService;
import com.davsilvam.pokedecks.services.UserService;
import com.davsilvam.pokedecks.services.dtos.CreateOrderRequestDTO;
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
@RequestMapping("/api/orders")
@AllArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido com os detalhes fornecidos.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido criado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida ou estoque insuficiente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário ou produto não encontrado",
                    content = @Content
            )
    })
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO request) {
        OrderResponseDTO response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter pedido por ID", description = "Retorna os detalhes de um pedido específico pelo seu ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido retornado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content
            )
    })
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable UUID id) {
        OrderResponseDTO response = orderService.getOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/my-orders")
    @Operation(
            summary = "Obter meus pedidos",
            description = "Retorna todos os pedidos do usuário autenticado. O usuário é identificado automaticamente pelo token JWT."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pedidos retornada com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = OrderResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado",
                    content = @Content
            )
    })
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(Authentication authentication) {
        String email = authentication.getName();
        UserResponseDTO currentUser = userService.findByEmail(email);
        List<OrderResponseDTO> response = orderService.getOrdersByUserId(currentUser.id());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Obter todos os pedidos",
            description = "Retorna uma lista de todos os pedidos disponíveis. Restrito a administradores."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pedidos retornada com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = OrderResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é administrador",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> response = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar pedido por ID",
            description = "Deleta um pedido específico pelo seu ID. Restrito a administradores."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Pedido deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é administrador",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrderById(@PathVariable UUID id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
