package com.davsilvam.pokedecks.controllers;

import com.davsilvam.pokedecks.services.CardService;
import com.davsilvam.pokedecks.services.dtos.CardBriefResponseDTO;
import com.davsilvam.pokedecks.services.dtos.CardResponseDTO;
import com.davsilvam.pokedecks.services.dtos.CreateCardRequestDTO;
import com.davsilvam.pokedecks.services.dtos.UpdateCardRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@AllArgsConstructor
@Tag(name = "Cartas", description = "Endpoints para gerenciamento de cartas")
public class CardController {
    private final CardService cardService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Obter carta por ID",
            description = "Retorna os detalhes completos de uma carta específica pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Carta retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = CardResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Carta não encontrada",
                    content = @Content
            )
    })
    public ResponseEntity<CardResponseDTO> getCardById(@PathVariable String id) {
        CardResponseDTO response = cardService.getCardById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Obter todas as cartas",
            description = "Retorna uma lista resumida de todas as cartas disponíveis no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de cartas retornada com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = CardBriefResponseDTO.class))
                    )
            )
    })
    public ResponseEntity<List<CardBriefResponseDTO>> getAllCards() {
        List<CardBriefResponseDTO> response = cardService.getAllCards();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Buscar cartas por nome",
            description = "Retorna uma lista de cartas que correspondem ao nome fornecido (busca case-insensitive)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de cartas retornada com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = CardBriefResponseDTO.class))
                    )
            )
    })
    public ResponseEntity<List<CardBriefResponseDTO>> searchCardByName(
            @Parameter(description = "Nome ou parte do nome da carta", example = "Pikachu")
            @RequestParam String name
    ) {
        List<CardBriefResponseDTO> response = cardService.searchCardsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @Operation(
            summary = "Criar nova carta",
            description = "Cria uma nova carta no sistema. Restrito a administradores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Carta criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CardBriefResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida - dados de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é administrador",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Coleção não encontrada",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito - carta com ID já existe",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CardBriefResponseDTO> createCard(@Valid @RequestBody CreateCardRequestDTO dto) {
        CardBriefResponseDTO response = cardService.createCard(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar carta por ID",
            description = "Atualiza os dados de uma carta específica pelo seu ID. Restrito a administradores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Carta atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CardBriefResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida - dados de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é administrador",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Carta ou coleção não encontrada",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CardBriefResponseDTO> updateCard(@PathVariable String id, @Valid @RequestBody UpdateCardRequestDTO dto) {
        CardBriefResponseDTO response = cardService.updateCard(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar carta por ID",
            description = "Deleta uma carta específica pelo seu ID. Restrito a administradores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Carta deletada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é administrador",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Carta não encontrada",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCardById(@PathVariable String id) {
        cardService.deleteCardById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
