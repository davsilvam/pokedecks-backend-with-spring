package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.config.errors.exceptions.ResourceConflictException;
import com.davsilvam.pokedecks.config.errors.exceptions.ResourceNotFoundException;
import com.davsilvam.pokedecks.models.Serie;
import com.davsilvam.pokedecks.models.Set;
import com.davsilvam.pokedecks.models.repositories.SerieRepository;
import com.davsilvam.pokedecks.models.repositories.SetRepository;
import com.davsilvam.pokedecks.services.dtos.CreateSerieRequestDTO;
import com.davsilvam.pokedecks.services.dtos.SerieResponseDTO;
import com.davsilvam.pokedecks.services.dtos.UpdateSerieRequestDTO;
import com.davsilvam.pokedecks.services.mappers.SerieMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SerieService {
    private final SerieRepository serieRepository;
    private final SetRepository setRepository;

    public SerieResponseDTO getSerieById(String id) {
        Serie serie = serieRepository.findById(id).orElse(null);

        if (serie == null) {
            throw new ResourceNotFoundException("Serie com ID " + id);
        }

        return SerieMapper.toDTO(serie);
    }

    public List<SerieResponseDTO> getAllSeries() {
        return serieRepository.findAll().stream()
                .map(SerieMapper::toDTO)
                .toList();
    }

    public SerieResponseDTO createSerie(CreateSerieRequestDTO dto) {
        if (serieRepository.existsById(dto.id())) {
            throw new ResourceConflictException("Série com ID " + dto.id() + " já existe");
        }

        Serie serie = Serie.builder()
                .id(dto.id())
                .name(dto.name())
                .logoUrl(dto.logoUrl())
                .build();

        Serie savedSerie = serieRepository.save(serie);
        return SerieMapper.toDTO(savedSerie);
    }

    public SerieResponseDTO updateSerie(String id, UpdateSerieRequestDTO dto) {
        Serie serie = serieRepository.findById(id).orElse(null);

        if (serie == null) {
            throw new ResourceNotFoundException("Serie com ID " + id);
        }

        if (dto.name() != null) {
            serie.setName(dto.name());
        }

        if (dto.logoUrl() != null) {
            serie.setLogoUrl(dto.logoUrl());
        }

        Serie updatedSerie = serieRepository.save(serie);
        return SerieMapper.toDTO(updatedSerie);
    }

    public void deleteSerieById(String id) {
        Serie serie = serieRepository.findById(id).orElse(null);

        if (serie == null) {
            throw new ResourceNotFoundException("Serie com ID " + id);
        }

        List<Set> sets = setRepository.findBySerieId(id);
        for (Set set : sets) {
            setRepository.delete(set);
        }

        serieRepository.deleteById(id);
    }
}
