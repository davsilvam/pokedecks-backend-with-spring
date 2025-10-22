package com.davsilvam.pokedecks.models.repositories;

import com.davsilvam.pokedecks.models.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetRepository extends JpaRepository<Set, String> {
    List<Set> findBySerieId(String id);
}
