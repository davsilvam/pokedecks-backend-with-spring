package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.config.errors.exceptions.ResourceConflictException;
import com.davsilvam.pokedecks.config.errors.exceptions.ResourceNotFoundException;
import com.davsilvam.pokedecks.models.*;
import com.davsilvam.pokedecks.models.repositories.*;
import com.davsilvam.pokedecks.services.dtos.CardBriefResponseDTO;
import com.davsilvam.pokedecks.services.dtos.CardResponseDTO;
import com.davsilvam.pokedecks.services.dtos.CreateCardRequestDTO;
import com.davsilvam.pokedecks.services.dtos.SetWithCardsResponseDTO;
import com.davsilvam.pokedecks.services.dtos.UpdateCardRequestDTO;
import com.davsilvam.pokedecks.services.mappers.CardMapper;
import com.davsilvam.pokedecks.services.mappers.SetMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardService {
    private final SetRepository setRepository;
    private final CardRepository cardRepository;
    private final PokemonRepository pokemonRepository;
    private final EnergyRepository energyRepository;
    private final TrainerRepository trainerRepository;

    public CardResponseDTO getCardById(String id) {
        Card card = cardRepository.findById(id).orElse(null);

        if (card == null) {
            throw new ResourceNotFoundException("Carta com ID " + id);
        }

        return mapCardToResponseDTO(card);
    }

    public List<CardBriefResponseDTO> getAllCards() {
        return cardRepository.findAll().stream()
                .map(CardMapper::toCardBriefResponseDTO)
                .toList();
    }

    public SetWithCardsResponseDTO getCardsBySetId(String setId) {
        Set set = setRepository.findById(setId).orElse(null);

        if (set == null) {
            throw new ResourceNotFoundException("Coleção com ID " + setId);
        }

        return new SetWithCardsResponseDTO(
                SetMapper.toDTO(set),
                cardRepository.findBySetId(setId).stream()
                        .map(CardMapper::toCardBriefResponseDTO)
                        .toList());
    }

    public List<CardBriefResponseDTO> searchCardsByName(String name) {
        return cardRepository.findByNameContainingIgnoreCase(name).stream()
                .map(CardMapper::toCardBriefResponseDTO)
                .toList();
    }

    public CardBriefResponseDTO createCard(CreateCardRequestDTO dto) {
        if (cardRepository.existsById(dto.id())) {
            throw new ResourceConflictException("Carta com ID " + dto.id() + " já existe");
        }

        Set set = setRepository.findById(dto.setId()).orElse(null);
        if (set == null) {
            throw new ResourceNotFoundException("Coleção com ID " + dto.setId());
        }

        Card card = Card.builder()
                .id(dto.id())
                .localId(dto.localId())
                .name(dto.name())
                .imageUrl(dto.imageUrl())
                .illustrator(dto.illustrator())
                .rarity(dto.rarity())
                .price(dto.price())
                .stockQuantity(dto.stockQuantity())
                .category(dto.category())
                .set(set)
                .build();

        Card savedCard = cardRepository.save(card);
        return CardMapper.toCardBriefResponseDTO(savedCard);
    }

    public CardBriefResponseDTO updateCard(String id, UpdateCardRequestDTO dto) {
        Card card = cardRepository.findById(id).orElse(null);

        if (card == null) {
            throw new ResourceNotFoundException("Carta com ID " + id);
        }

        if (dto.localId() != null) {
            card.setLocalId(dto.localId());
        }

        if (dto.name() != null) {
            card.setName(dto.name());
        }

        if (dto.imageUrl() != null) {
            card.setImageUrl(dto.imageUrl());
        }

        if (dto.illustrator() != null) {
            card.setIllustrator(dto.illustrator());
        }

        if (dto.rarity() != null) {
            card.setRarity(dto.rarity());
        }

        if (dto.price() != null) {
            card.setPrice(dto.price());
        }

        if (dto.stockQuantity() != null) {
            card.setStockQuantity(dto.stockQuantity());
        }

        if (dto.category() != null) {
            card.setCategory(dto.category());
        }

        if (dto.setId() != null) {
            Set set = setRepository.findById(dto.setId()).orElse(null);
            if (set == null) {
                throw new ResourceNotFoundException("Coleção com ID " + dto.setId());
            }
            card.setSet(set);
        }

        Card updatedCard = cardRepository.save(card);
        return CardMapper.toCardBriefResponseDTO(updatedCard);
    }

    public void deleteCardById(String id) {
        Card card = cardRepository.findById(id).orElse(null);

        if (card == null) {
            throw new ResourceNotFoundException("Carta com ID " + id);
        }

        switch (card.getCategory()) {
            case POKEMON -> {
                Pokemon pokemon = pokemonRepository.findByCardId(card.getId());
                if (pokemon != null) {
                    pokemonRepository.delete(pokemon);
                }
            }
            case ENERGY -> {
                Energy energy = energyRepository.findByCardId(card.getId());
                if (energy != null) {
                    energyRepository.delete(energy);
                }
            }
            case TRAINER -> {
                Trainer trainer = trainerRepository.findByCardId(card.getId());
                if (trainer != null) {
                    trainerRepository.delete(trainer);
                }
            }
        }

        cardRepository.deleteById(id);
    }

    private CardResponseDTO mapCardToResponseDTO(Card card) {
        switch (card.getCategory()) {
            case POKEMON -> {
                Pokemon pokemon = pokemonRepository.findByCardId(card.getId());
                return CardMapper.toCardResponseDTO(card, pokemon);
            }

            case ENERGY -> {
                Energy energy = energyRepository.findByCardId(card.getId());
                return CardMapper.toCardResponseDTO(card, energy);
            }

            case TRAINER -> {
                Trainer trainer = trainerRepository.findByCardId(card.getId());
                return CardMapper.toCardResponseDTO(card, trainer);
            }

            default -> {
                return null;
            }
        }
    }
}
