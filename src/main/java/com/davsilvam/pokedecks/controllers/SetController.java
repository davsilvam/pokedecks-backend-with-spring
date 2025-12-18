package com.davsilvam.pokedecks.controllers;

import com.davsilvam.pokedecks.services.CardService;
import com.davsilvam.pokedecks.services.SetService;
import com.davsilvam.pokedecks.services.dtos.CreateSetRequestDTO;
import com.davsilvam.pokedecks.services.dtos.SetResponseDTO;
import com.davsilvam.pokedecks.services.dtos.SetWithCardsResponseDTO;
import com.davsilvam.pokedecks.services.dtos.UpdateSetRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/api/sets")
@AllArgsConstructor
@Tag(name = "Coleção", description = "Endpoints para gerenciamento de sets")
public class SetController {
    private final SetService setService;
    private final CardService cardService;

    @GetMapping("/{id}")
    @Operation(summary = "Obter coleção por ID", description = "Retorna os detalhes de uma coleção específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coleção retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleção não encontrada", content = @Content)
    })
    public ResponseEntity<SetResponseDTO> getSetById(@PathVariable String id) {
        SetResponseDTO response = setService.getSetById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @Operation(summary = "Obter todas as coleções", description = "Retorna uma lista de todas as coleções disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de coleções retornada com sucesso")
    })
    public ResponseEntity<List<SetResponseDTO>> getAllSets() {
        List<SetResponseDTO> response = setService.getAllSets();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/cards")
    @Operation(summary = "Obter cartas por coleção ID", description = "Retorna uma lista de cartas pertencentes a uma coleção específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cartas retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleção não encontrada", content = @Content)
    })
    @Tag(name = "Cartas")
    public ResponseEntity<SetWithCardsResponseDTO> getCardsBySetId(@PathVariable String id) {
        SetWithCardsResponseDTO response = cardService.getCardsBySetId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @Operation(summary = "Criar nova coleção", description = "Cria uma nova coleção no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Coleção criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Série não encontrada", content = @Content),
            @ApiResponse(responseCode = "409", description = "Coleção com ID já existe", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SetResponseDTO> createSet(@Valid @RequestBody CreateSetRequestDTO dto) {
        SetResponseDTO response = setService.createSet(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar coleção por ID", description = "Atualiza os dados de uma coleção específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coleção atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleção ou série não encontrada", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SetResponseDTO> updateSet(@PathVariable String id, @Valid @RequestBody UpdateSetRequestDTO dto) {
        SetResponseDTO response = setService.updateSet(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar coleção por ID", description = "Deleta uma coleção específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Coleção deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleção não encontrada", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSetById(@PathVariable String id) {
        setService.deleteSetById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
