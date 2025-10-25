-- SQL Seed para produção (Render)
-- Dados iniciais do pokémon TCG

-- Limpar dados existentes (ordem correta para respeitar foreign keys)
DELETE FROM energies;
DELETE FROM trainers;
DELETE FROM pokemons;
DELETE FROM cards;
DELETE FROM sets;
DELETE FROM series;

-- Série: Base
INSERT INTO series (id, name, logo_url) VALUES ('base', 'Base', 'https://assets.tcgdex.net/en/base/base1/logo');

-- Set: Base Set
INSERT INTO sets (id, name, logo_url, release_date, serie_id) 
VALUES ('base1', 'Base Set', 'https://assets.tcgdex.net/en/base/base1/logo', NOW(), 'base');

-- Pokémons Raros
INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-4', 4, 'Charizard', 'https://assets.tcgdex.net/en/base/base1/4', 'Mitsuhiro Arita', 'Rare', 3.46, 'POKEMON', 'base1');
INSERT INTO pokemons (card_id, dex_id, hp, types, stage, description, level)
VALUES ('base1-4', 6, 120, 'Fire', 'Stage2', NULL, 1);

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-2', 2, 'Blastoise', 'https://assets.tcgdex.net/en/base/base1/2', 'Ken Sugimori', 'Rare', 10.12, 'POKEMON', 'base1');
INSERT INTO pokemons (card_id, dex_id, hp, types, stage, description, level)
VALUES ('base1-2', 9, 100, 'Water', 'Stage2', NULL, 1);

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-15', 15, 'Venusaur', 'https://assets.tcgdex.net/en/base/base1/15', 'Mitsuhiro Arita', 'Rare', 10.12, 'POKEMON', 'base1');
INSERT INTO pokemons (card_id, dex_id, hp, types, stage, description, level)
VALUES ('base1-15', 3, 100, 'Grass', 'Stage2', NULL, 1);

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-10', 10, 'Mewtwo', 'https://assets.tcgdex.net/en/base/base1/10', 'Ken Sugimori', 'Rare', 6.54, 'POKEMON', 'base1');
INSERT INTO pokemons (card_id, dex_id, hp, types, stage, description, level)
VALUES ('base1-10', 150, 60, 'Psychic', 'Basic', NULL, 1);

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-16', 16, 'Zapdos', 'https://assets.tcgdex.net/en/base/base1/16', 'Ken Sugimori', 'Rare', 2.49, 'POKEMON', 'base1');
INSERT INTO pokemons (card_id, dex_id, hp, types, stage, description, level)
VALUES ('base1-16', 145, 90, 'Lightning', 'Basic', NULL, 1);

-- Pokémons Iniciais
INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-46', 46, 'Charmander', 'https://assets.tcgdex.net/en/base/base1/46', 'Mitsuhiro Arita', 'Common', 10.27, 'POKEMON', 'base1');
INSERT INTO pokemons (card_id, dex_id, hp, types, stage, description, level)
VALUES ('base1-46', 4, 50, 'Fire', 'Basic', NULL, 1);

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-63', 63, 'Squirtle', 'https://assets.tcgdex.net/en/base/base1/63', 'Mitsuhiro Arita', 'Common', 8.6, 'POKEMON', 'base1');
INSERT INTO pokemons (card_id, dex_id, hp, types, stage, description, level)
VALUES ('base1-63', 7, 40, 'Water', 'Basic', NULL, 1);

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-44', 44, 'Bulbasaur', 'https://assets.tcgdex.net/en/base/base1/44', 'Mitsuhiro Arita', 'Common', 3.9, 'POKEMON', 'base1');
INSERT INTO pokemons (card_id, dex_id, hp, types, stage, description, level)
VALUES ('base1-44', 1, 40, 'Grass', 'Basic', NULL, 1);

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-58', 58, 'Pikachu', 'https://assets.tcgdex.net/en/base/base1/58', 'Mitsuhiro Arita', 'Common', 8.71, 'POKEMON', 'base1');
INSERT INTO pokemons (card_id, dex_id, hp, types, stage, description, level)
VALUES ('base1-58', 25, 40, 'Lightning', 'Basic', NULL, 1);

-- Trainers
INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-88', 88, 'Professor Oak', 'https://assets.tcgdex.net/en/base/base1/88', 'Ken Sugimori', 'Uncommon', 8.57, 'TRAINER', 'base1');
INSERT INTO trainers (card_id, effect, type)
VALUES ('base1-88', 'Draw 7 cards', 'Supporter');

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-91', 91, 'Bill', 'https://assets.tcgdex.net/en/base/base1/91', 'Ken Sugimori', 'Common', 6.41, 'TRAINER', 'base1');
INSERT INTO trainers (card_id, effect, type)
VALUES ('base1-91', 'Draw 2 cards', 'Supporter');

-- Energias Básicas
INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-98', 98, 'Fire Energy', 'https://assets.tcgdex.net/en/base/base1/98', 'Keiji Kinebuchi', 'Common', 2.62, 'ENERGY', 'base1');
INSERT INTO energies (card_id, effect, type)
VALUES ('base1-98', 'Provides Fire energy', 'Normal');

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-102', 102, 'Water Energy', 'https://assets.tcgdex.net/en/base/base1/102', 'Keiji Kinebuchi', 'Common', 2.53, 'ENERGY', 'base1');
INSERT INTO energies (card_id, effect, type)
VALUES ('base1-102', 'Provides Water energy', 'Normal');

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-99', 99, 'Grass Energy', 'https://assets.tcgdex.net/en/base/base1/99', 'Keiji Kinebuchi', 'Common', 3.25, 'ENERGY', 'base1');
INSERT INTO energies (card_id, effect, type)
VALUES ('base1-99', 'Provides Grass energy', 'Normal');

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-100', 100, 'Lightning Energy', 'https://assets.tcgdex.net/en/base/base1/100', 'Keiji Kinebuchi', 'Common', 6.23, 'ENERGY', 'base1');
INSERT INTO energies (card_id, effect, type)
VALUES ('base1-100', 'Provides Lightning energy', 'Normal');

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-101', 101, 'Psychic Energy', 'https://assets.tcgdex.net/en/base/base1/101', 'Keiji Kinebuchi', 'Common', 3.15, 'ENERGY', 'base1');
INSERT INTO energies (card_id, effect, type)
VALUES ('base1-101', 'Provides Psychic energy', 'Normal');

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-97', 97, 'Fighting Energy', 'https://assets.tcgdex.net/en/base/base1/97', 'Keiji Kinebuchi', 'Common', 1.35, 'ENERGY', 'base1');
INSERT INTO energies (card_id, effect, type)
VALUES ('base1-97', 'Provides Fighting energy', 'Normal');

INSERT INTO cards (id, local_id, name, image_url, illustrator, rarity, price, category, set_id)
VALUES ('base1-96', 96, 'Double Colorless Energy', 'https://assets.tcgdex.net/en/base/base1/96', 'Keiji Kinebuchi', 'Uncommon', 3.01, 'ENERGY', 'base1');
INSERT INTO energies (card_id, effect, type)
VALUES ('base1-96', 'Provides 2 Colorless energy', 'Special');
