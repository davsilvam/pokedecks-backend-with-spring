package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.config.errors.exceptions.ResourceNotFoundException;
import com.davsilvam.pokedecks.models.Serie;
import com.davsilvam.pokedecks.models.Set;
import com.davsilvam.pokedecks.models.repositories.SerieRepository;
import com.davsilvam.pokedecks.models.repositories.SetRepository;
import com.davsilvam.pokedecks.services.dtos.SetResponseDTO;
import com.davsilvam.pokedecks.services.mappers.SetMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SetService {
    private final SerieRepository serieRepository;
    private final SetRepository setRepository;

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

    public void deleteSetById(String id) {
        Set set = setRepository.findById(id).orElse(null);

        if (set == null) {
            throw new ResourceNotFoundException("Coleção com ID " + id);
        }

        setRepository.deleteById(id);
    }
}
