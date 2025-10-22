package com.davsilvam.pokedecks.models.repositories;

import com.davsilvam.pokedecks.models.Energy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyRepository extends JpaRepository<Energy, String> {
    Energy findByCardId(String cardId);
}
