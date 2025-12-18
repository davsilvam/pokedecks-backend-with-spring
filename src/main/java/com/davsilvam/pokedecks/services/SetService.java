package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.config.errors.exceptions.ResourceConflictException;
import com.davsilvam.pokedecks.config.errors.exceptions.ResourceNotFoundException;
import com.davsilvam.pokedecks.models.*;
import com.davsilvam.pokedecks.models.repositories.*;
import com.davsilvam.pokedecks.services.dtos.CreateSetRequestDTO;
import com.davsilvam.pokedecks.services.dtos.SetResponseDTO;
import com.davsilvam.pokedecks.services.dtos.UpdateSetRequestDTO;
import com.davsilvam.pokedecks.services.mappers.SetMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SetService {
    private final SerieRepository serieRepository;
    private final SetRepository setRepository;
    private final CardRepository cardRepository;
    private final PokemonRepository pokemonRepository;
    private final EnergyRepository energyRepository;
    private final TrainerRepository trainerRepository;

    public SetResponseDTO getSetById(String id) {
        Set set = setRepository.findById(id).orElse(null);

        if (set == null) {
            throw new ResourceNotFoundException("Coleção com ID " + id);
        }

        return SetMapper.toDTO(set);
    }

    public List<SetResponseDTO> getAllSets() {
        return setRepository.findAll().stream()
                .map(SetMapper::toDTO)
                .toList();
    }

    public List<SetResponseDTO> getSetsBySerieId(String serieId) {
        Serie serie = serieRepository.findById(serieId).orElse(null);

        if (serie == null) {
            throw new ResourceNotFoundException("Série com ID " + serieId);
        }

        return setRepository.findBySerieId(serieId).stream()
                .map(SetMapper::toDTO)
                .toList();
    }

    public SetResponseDTO createSet(CreateSetRequestDTO dto) {
        if (setRepository.existsById(dto.id())) {
            throw new ResourceConflictException("Coleção com ID " + dto.id() + " já existe");
        }

        Serie serie = serieRepository.findById(dto.serieId()).orElse(null);
        if (serie == null) {
            throw new ResourceNotFoundException("Série com ID " + dto.serieId());
        }

        Set set = Set.builder()
                .id(dto.id())
                .name(dto.name())
                .logoUrl(dto.logoUrl())
                .releaseDate(dto.releaseDate())
                .serie(serie)
                .build();

        Set savedSet = setRepository.save(set);
        return SetMapper.toDTO(savedSet);
    }

    public SetResponseDTO updateSet(String id, UpdateSetRequestDTO dto) {
        Set set = setRepository.findById(id).orElse(null);

        if (set == null) {
            throw new ResourceNotFoundException("Coleção com ID " + id);
        }

        if (dto.name() != null) {
            set.setName(dto.name());
        }

        if (dto.logoUrl() != null) {
            set.setLogoUrl(dto.logoUrl());
        }

        if (dto.releaseDate() != null) {
            set.setReleaseDate(dto.releaseDate());
        }

        if (dto.serieId() != null) {
            Serie serie = serieRepository.findById(dto.serieId()).orElse(null);
            if (serie == null) {
                throw new ResourceNotFoundException("Série com ID " + dto.serieId());
            }
            set.setSerie(serie);
        }

        Set updatedSet = setRepository.save(set);
        return SetMapper.toDTO(updatedSet);
    }

    public void deleteSetById(String id) {
        Set set = setRepository.findById(id).orElse(null);

        if (set == null) {
            throw new ResourceNotFoundException("Coleção com ID " + id);
        }

        List<Card> cards = cardRepository.findBySetId(id);
        for (Card card : cards) {
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
            cardRepository.delete(card);
        }

        setRepository.deleteById(id);
    }
}
