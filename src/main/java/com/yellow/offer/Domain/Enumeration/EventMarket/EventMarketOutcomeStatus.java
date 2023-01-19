package com.yellow.offer.Domain.Enumeration.EventMarket;

public enum EventMarketOutcomeStatus {
    INACTIVE, //Event market outcome  is inactive and any event market of its type should not be delivered to the client.
    ACTIVE //Event market outcome  is active and any event market of its type should be delivered to the client if other conditions are met.
}
