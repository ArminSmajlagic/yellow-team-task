package com.yellow.offer.Domain.Enumeration.Market;

public enum MarketOutcomeStatus {
    INACTIVE, //Market outcome is inactive and any event market outcome of its type should not be delivered to the client.
    ACTIVE //Market outcome is active and any event market outcome of its type should be delivered to the client if other conditions are met.
}
