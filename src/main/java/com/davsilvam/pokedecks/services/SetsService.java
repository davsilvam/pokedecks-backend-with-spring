package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.models.Serie;
import com.davsilvam.pokedecks.models.repositories.SerieRepository;
import com.davsilvam.pokedecks.models.repositories.SetRepository;
import com.davsilvam.pokedecks.services.dtos.SetResponseDTO;
import com.davsilvam.pokedecks.services.mappers.SetMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SetsService {
    private final SerieRepository serieRepository;
    private final SetRepository setRepository;

    public SetResponseDTO getSetById(String id) {
        return SetMapper.toDTO(setRepository.findById(id).orElse(null));
    }

    public List<SetResponseDTO> getAllSets() {
        return setRepository.findAll().stream()
                .map(SetMapper::toDTO)
                .toList();
    }

    public List<SetResponseDTO> getSetsBySerieId(String serieId) {
        Serie serie = serieRepository.findById(serieId).orElse(null);

        if (serie == null) {
            throw new IllegalArgumentException("Série não encontrada");
        }

        return setRepository.findBySerieId(serieId).stream()
                .map(SetMapper::toDTO)
                .toList();
    }

    public void deleteSetById(String id) {
        setRepository.deleteById(id);
    }
}
