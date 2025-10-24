package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.models.*;
import com.davsilvam.pokedecks.models.repositories.*;
import com.davsilvam.pokedecks.services.dtos.CardBriefResponseDTO;
import com.davsilvam.pokedecks.services.dtos.CardResponseDTO;
import com.davsilvam.pokedecks.services.dtos.SetWithCardsResponseDTO;
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

//    public CardResponseDTO createCard(Card card) {
//        return cardRepository.save(card);
//    }

    public CardResponseDTO getCardById(String id) {
        Card card = cardRepository.findById(id).orElse(null);

        if (card == null) {
            return null;
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
            throw new IllegalArgumentException("Coleção não encontrada");
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

    public void deleteCardById(String id) {
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
