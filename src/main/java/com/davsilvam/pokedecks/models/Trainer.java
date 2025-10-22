package com.davsilvam.pokedecks.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "trainers")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = "card")
public class Trainer {
    @Id
    private String id;

    @Column(name = "effect", nullable = false)
    private String effect;

    @Column(name = "type", nullable = false)
    private String type;

    // --- 1:1 Relationships ---
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;
}
