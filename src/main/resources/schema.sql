CREATE TABLE IF NOT EXISTS Offer (
    OFFER_ID BIGINT PRIMARY KEY,
    BRAND_ID INTEGER,
    START_DATE TIMESTAMP,
    END_DATE TIMESTAMP,
    PRICE_LIST INTEGER,
    PARTNUMBER VARCHAR(255),
    PRIORITY INTEGER,
    PRICE DECIMAL,
    CURR VARCHAR(255)
);