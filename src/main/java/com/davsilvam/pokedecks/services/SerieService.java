package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.models.repositories.SerieRepository;
import com.davsilvam.pokedecks.services.dtos.SerieResponseDTO;
import com.davsilvam.pokedecks.services.mappers.SerieMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SerieService {
    private final SerieRepository serieRepository;

    public SerieResponseDTO getSerieById(String id) {
        return SerieMapper.toDTO(serieRepository.findById(id).orElse(null));
    }

    public List<SerieResponseDTO> getAllSeries() {
        return serieRepository.findAll().stream()
                .map(SerieMapper::toDTO)
                .toList();
    }

    public void deleteSerie(String id) {
        serieRepository.deleteById(id);
    }
}
