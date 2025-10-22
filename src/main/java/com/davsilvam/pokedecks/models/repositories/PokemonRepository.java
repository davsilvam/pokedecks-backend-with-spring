package com.davsilvam.pokedecks.models.repositories;

import com.davsilvam.pokedecks.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, String> {
    Pokemon findByCardId(String cardId);
}
