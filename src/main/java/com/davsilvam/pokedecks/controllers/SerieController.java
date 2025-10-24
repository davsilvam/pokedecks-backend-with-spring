package com.davsilvam.pokedecks.controllers;

import com.davsilvam.pokedecks.services.SerieService;
import com.davsilvam.pokedecks.services.SetService;
import com.davsilvam.pokedecks.services.dtos.SerieResponseDTO;
import com.davsilvam.pokedecks.services.dtos.SetResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "Obter série por ID", description = "Retorna os detalhes de uma série específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Série retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Série não encontrada")
    })
    public ResponseEntity<SerieResponseDTO> getSerieById(@PathVariable String id) {
        SerieResponseDTO response = serieService.getSerieById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @Operation(summary = "Obter todas as séries", description = "Retorna uma lista de todas as séries disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de séries retornada com sucesso")
    })
    public ResponseEntity<List<SerieResponseDTO>> getAllSeries() {
        List<SerieResponseDTO> response = serieService.getAllSeries();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/sets")
    @Operation(summary = "Obter coleções por série ID", description = "Retorna uma lista de coleções pertencentes a uma série específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de coleções retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Série não encontrada")
    })
    public ResponseEntity<List<SetResponseDTO>> getSetsBySerieId(@PathVariable String id) {
        List<SetResponseDTO> response = setService.getSetsBySerieId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar série por ID", description = "Deleta uma série específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Série deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Série não encontrada")
    })
    public ResponseEntity<Void> deleteSerieById(@PathVariable String id) {
        serieService.deleteSerieById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
