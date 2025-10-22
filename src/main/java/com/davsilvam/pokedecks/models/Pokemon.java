package com.davsilvam.pokedecks.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "pokemons")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = "card")
public class Pokemon {
    @Id
    private String id;

    @Column(name = "dex_id", nullable = false)
    private int dexId;

    @Column(name = "hp", nullable = false)
    private int hp;

    @Column(name = "types")
    private String types; // Comma-separated types

    @Column(name = "stage", nullable = false)
    private String stage;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "level", nullable = false)
    private int level;

    // --- 1:1 Relationships ---
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;
}
