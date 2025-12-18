package com.davsilvam.pokedecks.services.dtos;

import com.davsilvam.pokedecks.models.enums.CardCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateCardRequestDTO(
        @NotBlank(message = "ID é obrigatório")
        String id,

        @NotNull(message = "Local ID é obrigatório")
        Integer localId,

        @NotBlank(message = "Nome é obrigatório")
        String name,

        String imageUrl,

        String illustrator,

        @NotBlank(message = "Raridade é obrigatória")
        String rarity,

        @NotNull(message = "Preço é obrigatório")
        @PositiveOrZero(message = "Preço deve ser maior ou igual a zero")
        Double price,

        @NotNull(message = "Quantidade em estoque é obrigatória")
        @PositiveOrZero(message = "Quantidade em estoque deve ser maior ou igual a zero")
        Integer stockQuantity,

        @NotNull(message = "Categoria é obrigatória")
        CardCategory category,

        @NotBlank(message = "ID da coleção é obrigatório")
        String setId
) {
}
