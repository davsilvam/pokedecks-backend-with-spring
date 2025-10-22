package com.davsilvam.pokedecks.models;

import com.davsilvam.pokedecks.models.enums.CardCategory;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = "id")
public class Card {
    @Id
    private String id;

    @Column(name = "local_id", nullable = false)
    private Integer localId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "illustrator")
    private String illustrator;

    @Column(name = "rarity", nullable = false)
    private String rarity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CardCategory category;

    // --- 1:N Relationships ---
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // --- N:1 Relationships ---
    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    private Set set;
}
