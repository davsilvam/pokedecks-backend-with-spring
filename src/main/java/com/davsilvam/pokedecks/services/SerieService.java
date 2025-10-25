package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.config.errors.exceptions.ResourceNotFoundException;
import com.davsilvam.pokedecks.models.Serie;
import com.davsilvam.pokedecks.models.repositories.SerieRepository;
import com.davsilvam.pokedecks.services.dtos.SerieResponseDTO;
import com.davsilvam.pokedecks.services.mappers.SerieMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SerieService {
    private final SerieRepository serieRepository;

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

    public void deleteSerieById(String id) {
        Serie serie = serieRepository.findById(id).orElse(null);

        if (serie == null) {
            throw new ResourceNotFoundException("Serie com ID " + id);
        }

        serieRepository.deleteById(id);
    }
}
