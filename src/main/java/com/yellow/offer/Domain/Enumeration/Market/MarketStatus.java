package com.yellow.offer.Domain.Enumeration.Market;

public enum MarketStatus {
    INACTIVE, //Market is inactive and any event market of its type should not be delivered to the client.
    ACTIVE //Market is active and any event market of its type should be delivered to the client if other conditions are met.
}
