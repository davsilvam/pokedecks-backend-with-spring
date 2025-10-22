package com.davsilvam.pokedecks.models.repositories;

import com.davsilvam.pokedecks.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<Serie, String> {
}
