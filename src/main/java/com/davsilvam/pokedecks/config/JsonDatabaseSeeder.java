package com.davsilvam.pokedecks.config;

import com.davsilvam.pokedecks.models.*;
import com.davsilvam.pokedecks.models.enums.CardCategory;
import com.davsilvam.pokedecks.models.repositories.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
public class JsonDatabaseSeeder implements CommandLineRunner {
    private final SerieRepository serieRepo;
    private final SetRepository setRepo;
    private final CardRepository cardRepo;
    private final PokemonRepository pokemonRepo;
    private final TrainerRepository trainerRepo;
    private final EnergyRepository energyRepo;

    public JsonDatabaseSeeder(
            SerieRepository serieRepo,
            SetRepository setRepo,
            CardRepository cardRepo,
            PokemonRepository pokemonRepo,
            TrainerRepository trainerRepo,
            EnergyRepository energyRepo
    ) {
        this.serieRepo = serieRepo;
        this.setRepo = setRepo;
        this.cardRepo = cardRepo;
        this.pokemonRepo = pokemonRepo;
        this.trainerRepo = trainerRepo;
        this.energyRepo = energyRepo;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (serieRepo.count() > 0) {
            System.out.println("⚠️ Database already seeded. Skipping...");
            return;
        }

        System.out.println("🧹 Limpando tabelas antes do seed...");

        // Delete in correct order to respect foreign key constraints
        energyRepo.deleteAll();
        trainerRepo.deleteAll();
        pokemonRepo.deleteAll();
        cardRepo.deleteAll();
        setRepo.deleteAll();
        serieRepo.deleteAll();

        // Flush to ensure deletes are committed
        energyRepo.flush();
        trainerRepo.flush();
        pokemonRepo.flush();
        cardRepo.flush();
        setRepo.flush();
        serieRepo.flush();

        System.out.println("🌱 Iniciando seed com JSON...");

        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new ClassPathResource("sample.json").getFile();
        JsonNode root = mapper.readTree(jsonFile);

        for (JsonNode serieNode : root) {
            Serie serie = Serie.builder()
                    .id(serieNode.get("id").asText())
                    .name(serieNode.get("name").asText())
                    .logoUrl(serieNode.has("logo") ? serieNode.get("logo").asText() : null)
                    .sets(new ArrayList<>())
                    .build();

            // Salva a série primeiro
            serie = serieRepo.saveAndFlush(serie);

            JsonNode setNode = serieNode.get("set");
            Set set = Set.builder()
                    .id(setNode.get("id").asText())
                    .name(setNode.get("name").asText())
                    .logoUrl(setNode.has("logo") ? setNode.get("logo").asText() : null)
                    .releaseDate(LocalDateTime.now())
                    .cards(new ArrayList<>())
                    .serie(serie)
                    .build();

            set = setRepo.saveAndFlush(set);

            for (JsonNode cardNode : setNode.get("cards")) {
                String cardId = cardNode.get("id").asText();

                CardCategory category;

                try {
                    category = CardCategory.valueOf(cardNode.get("category").asText().toUpperCase());
                } catch (Exception e) {
                    category = CardCategory.POKEMON;
                }

                Card card = Card.builder()
                        .id(cardId)
                        .localId(cardNode.has("localId") ? cardNode.get("localId").asInt() : 0)
                        .name(cardNode.get("name").asText())
                        .imageUrl(cardNode.has("image") ? cardNode.get("image").asText() : null)
                        .illustrator(cardNode.has("illustrator") ? cardNode.get("illustrator").asText() : "Unknown")
                        .rarity(cardNode.has("rarity") ? cardNode.get("rarity").asText() : "Common")
                        .price(Math.random() * 10 + 1)
                        .category(category)
                        .set(set)
                        .build();

                // Salva o card antes de criar entidades dependentes
                card = cardRepo.saveAndFlush(card);
                set.getCards().add(card);

                // Cria entidades dependentes
                switch (category) {
                    case POKEMON -> {
                        Pokemon pokemon = Pokemon.builder()
                                .card(card)
                                .dexId(cardNode.has("dexId") ? cardNode.get("dexId").isArray() ?
                                        cardNode.get("dexId").get(0).asInt() :
                                        cardNode.get("dexId").asInt() : 0)
                                .hp(cardNode.has("hp") ? cardNode.get("hp").asInt() : 0)
                                .types(cardNode.has("types") ? String.join(",", mapper.convertValue(cardNode.get("types"), List.class)) : null)
                                .stage(cardNode.has("stage") ? cardNode.get("stage").asText() : "Basic")
                                .description(cardNode.has("description") ? cardNode.get("description").asText() : null)
                                .level(cardNode.has("level") ? cardNode.get("level").asInt() : 1)
                                .build();
                        pokemonRepo.saveAndFlush(pokemon);
                    }
                    case TRAINER -> {
                        Trainer trainer = Trainer.builder()
                                .card(card)
                                .effect(cardNode.has("effect") ? cardNode.get("effect").asText() : "No effect")
                                .type(cardNode.has("trainerType") ? cardNode.get("trainerType").asText() : "Supporter")
                                .build();
                        trainerRepo.saveAndFlush(trainer);
                    }
                    case ENERGY -> {
                        Energy energy = Energy.builder()
                                .card(card)
                                .effect(cardNode.has("effect") ? cardNode.get("effect").asText() : "Provides energy")
                                .type(cardNode.has("energyType") ? cardNode.get("energyType").asText() : "Basic")
                                .build();
                        energyRepo.saveAndFlush(energy);
                    }
                }
            }

            // Atualiza a série com o set
            serie.getSets().add(set);
            serieRepo.saveAndFlush(serie);

            System.out.println("✅ Série importada: " + serie.getName());
        }

        System.out.println("🎉 Seed concluído com sucesso!");
    }
}