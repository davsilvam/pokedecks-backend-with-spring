package com.davsilvam.pokedecks.services.dtos;

import com.davsilvam.pokedecks.models.enums.CardCategory;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateCardRequestDTO(
        Integer localId,
        String name,
        String imageUrl,
        String illustrator,
        String rarity,

        @PositiveOrZero(message = "Preço deve ser maior ou igual a zero")
        Double price,

        @PositiveOrZero(message = "Quantidade em estoque deve ser maior ou igual a zero")
        Integer stockQuantity,

        CardCategory category,
        String setId
) {
}
