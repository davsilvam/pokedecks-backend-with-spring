package com.davsilvam.pokedecks.controllers;

import com.davsilvam.pokedecks.services.SerieService;
import com.davsilvam.pokedecks.services.SetService;
import com.davsilvam.pokedecks.services.dtos.CreateSerieRequestDTO;
import com.davsilvam.pokedecks.services.dtos.SerieResponseDTO;
import com.davsilvam.pokedecks.services.dtos.SetResponseDTO;
import com.davsilvam.pokedecks.services.dtos.UpdateSerieRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/series")
@AllArgsConstructor
@Tag(name = "Séries", description = "Endpoints para gerenciamento de séries")
public class SerieController {
    private final SerieService serieService;
    private final SetService setService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Obter série por ID",
            description = "Retorna os detalhes de uma série específica pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Série retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = SerieResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Série não encontrada",
                    content = @Content
            )
    })
    public ResponseEntity<SerieResponseDTO> getSerieById(@PathVariable String id) {
        SerieResponseDTO response = serieService.getSerieById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Obter todas as séries",
            description = "Retorna uma lista de todas as séries disponíveis no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de séries retornada com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = SerieResponseDTO.class))
                    )
            )
    })
    public ResponseEntity<List<SerieResponseDTO>> getAllSeries() {
        List<SerieResponseDTO> response = serieService.getAllSeries();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/sets")
    @Operation(
            summary = "Obter coleções por série ID",
            description = "Retorna uma lista de coleções pertencentes a uma série específica pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de coleções retornada com sucesso",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = SetResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Série não encontrada",
                    content = @Content
            )
    })
    public ResponseEntity<List<SetResponseDTO>> getSetsBySerieId(@PathVariable String id) {
        List<SetResponseDTO> response = setService.getSetsBySerieId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @Operation(
            summary = "Criar nova série",
            description = "Cria uma nova série no sistema. Restrito a administradores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Série criada com sucesso",
                    content = @Content(schema = @Schema(implementation = SerieResponseDTO.class))
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
                    responseCode = "409",
                    description = "Conflito - série com ID já existe",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SerieResponseDTO> createSerie(@Valid @RequestBody CreateSerieRequestDTO dto) {
        SerieResponseDTO response = serieService.createSerie(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar série por ID",
            description = "Atualiza os dados de uma série específica pelo seu ID. Restrito a administradores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Série atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = SerieResponseDTO.class))
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
                    description = "Série não encontrada",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SerieResponseDTO> updateSerie(@PathVariable String id, @Valid @RequestBody UpdateSerieRequestDTO dto) {
        SerieResponseDTO response = serieService.updateSerie(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar série por ID",
            description = "Deleta uma série específica pelo seu ID. Restrito a administradores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Série deletada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é administrador",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Série não encontrada",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSerieById(@PathVariable String id) {
        serieService.deleteSerieById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
