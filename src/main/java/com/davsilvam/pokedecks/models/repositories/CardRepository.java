package com.davsilvam.pokedecks.models.repositories;

import com.davsilvam.pokedecks.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    List<Card> findBySetId(String setId);

    List<Card> findByNameContainingIgnoreCase(String name);
}
