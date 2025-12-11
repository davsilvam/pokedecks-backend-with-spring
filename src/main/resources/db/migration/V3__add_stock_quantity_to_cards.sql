ALTER TABLE cards
    ADD COLUMN stock_quantity INTEGER NOT NULL DEFAULT 0;

UPDATE cards
SET stock_quantity = 100
WHERE stock_quantity = 0;
