package com.davsilvam.pokedecks.services.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateSetRequestDTO(
        @NotBlank(message = "ID é obrigatório")
        String id,

        @NotBlank(message = "Nome é obrigatório")
        String name,

        String logoUrl,

        @NotNull(message = "Data de lançamento é obrigatória")
        LocalDateTime releaseDate,

        @NotBlank(message = "ID da série é obrigatório")
        String serieId
) {
}
