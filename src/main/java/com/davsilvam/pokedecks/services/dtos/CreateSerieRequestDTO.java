package com.davsilvam.pokedecks.services.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateSerieRequestDTO(
        @NotBlank(message = "ID é obrigatório")
        String id,

        @NotBlank(message = "Nome é obrigatório")
        String name,

        String logoUrl
) {
}
