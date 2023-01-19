package com.yellow.offer.Domain.Enumeration.Event;

public enum EventStatus {
    INACTIVE, //Event is inactive, and it should not be delivered to the client.
    ACTIVE //Event is active, and it should be delivered to the client.
}
