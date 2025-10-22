package com.davsilvam.pokedecks.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sets")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = "id")
public class Set {
    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "release_date", nullable = false)
    private LocalDateTime releaseDate;

    // --- 1:N Relationships ---
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "set_id")
    private List<Card> cards = new ArrayList<>();

    // --- N:1 Relationships ---
    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;
}
