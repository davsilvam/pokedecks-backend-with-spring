package com.davsilvam.pokedecks.controllers;

import com.davsilvam.pokedecks.services.CardService;
import com.davsilvam.pokedecks.services.dtos.CardBriefResponseDTO;
import com.davsilvam.pokedecks.services.dtos.CardResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@AllArgsConstructor
@Tag(name = "Cartas", description = "Endpoints para gerenciamento de cartas")
public class CardController {
    private final CardService cardService;

    @GetMapping("/{id}")
    @Operation(summary = "Obter carta por ID", description = "Retorna os detalhes de uma carta específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carta retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Carta não encontrada", content = @Content)
    })
    public ResponseEntity<CardResponseDTO> getCardById(@PathVariable String id) {
        CardResponseDTO response = cardService.getCardById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @Operation(summary = "Obter todas as cartas", description = "Retorna uma lista de todas as cartas disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cartas retornada com sucesso")
    })
    public ResponseEntity<List<CardBriefResponseDTO>> getAllCards() {
        List<CardBriefResponseDTO> response = cardService.getAllCards();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar cartas por nome", description = "Retorna uma lista de cartas que correspondem ao nome fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cartas retornada com sucesso")
    })
    public ResponseEntity<List<CardBriefResponseDTO>> searchCardByName(@RequestParam String name) {
        List<CardBriefResponseDTO> response = cardService.searchCardsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar carta por ID", description = "Deleta uma carta específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carta deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Carta não encontrada", content = @Content)
    })
    public ResponseEntity<Void> deleteCardById(@PathVariable String id) {
        cardService.deleteCardById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
