-- This migration is not being used
CREATE TABLE Market(
    id VARCHAR(10) PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    status VARCHAR(10) NOT NULL,
    createdAt timestamp NOT NULL default NOW(),
    updatedAt timestamp NULL
);
CREATE TABLE MarketOutcome(
    id VARCHAR(10) PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    market_id VARCHAR(10),
    status VARCHAR(10) NOT NULL,
    createdAt timestamp NOT NULL default NOW(),
    updatedAt timestamp NULL,
    CONSTRAINT fk_market_outcome_market FOREIGN KEY(market_id) REFERENCES Market(id)
);
CREATE TABLE Event(
    id VARCHAR(10) PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    startsAt timestamp,
    status VARCHAR(10) NOT NULL,
    createdAt timestamp NOT NULL default NOW(),
    updatedAt timestamp NULL
);
CREATE TABLE EventMarket(
    id VARCHAR(10) PRIMARY KEY NOT NULL,
    market_id VARCHAR(10) NOT NULL,
    event_id VARCHAR(10) NOT NULL,
    status VARCHAR(10) NOT NULL,
    createdAt timestamp NOT NULL default NOW(),
    updatedAt timestamp NULL,
    CONSTRAINT fk_event_market_market FOREIGN KEY(market_id) REFERENCES Market(id),
    CONSTRAINT fk_event_market_event FOREIGN KEY(event_id) REFERENCES Event(id)
);
CREATE TABLE EventMarketOutcome(
    id VARCHAR(10) PRIMARY KEY NOT NULL,
    outcome_id VARCHAR(10) NOT NULL,
    event_market_id VARCHAR(10) NOT NULL,
    status VARCHAR(10) NOT NULL,
    odd decimal NOT NULL,
    createdAt timestamp NOT NULL default NOW(),
    updatedAt timestamp NULL,
    CONSTRAINT fk_event_market_outcome_outcome FOREIGN KEY(outcome_id) REFERENCES MarketOutcome(id),
    CONSTRAINT fk_event_market_outcome_event_market FOREIGN KEY(event_market_id) REFERENCES EventMarket(id)
    --Missing logical connection to event; Possible solution connection to event
);



