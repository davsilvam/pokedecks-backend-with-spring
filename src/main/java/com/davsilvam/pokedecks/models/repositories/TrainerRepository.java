package com.davsilvam.pokedecks.models.repositories;

import com.davsilvam.pokedecks.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, String> {
    Trainer findByCardId(String cardId);
}
