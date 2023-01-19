package com.yellow.offer.Application.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yellow.offer.Domain.Entities.Offer.Event.Event;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Infrastructure.Repositories.Offer.Events.EventRepository;
import com.yellow.offer.Infrastructure.Repositories.Offer.Markets.MarketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class ApplicationSeed {
    @Autowired
    private MarketRepository marketRepository;
    @Autowired
    private EventRepository eventRepository;
    @EventListener(ApplicationReadyEvent.class)
    public void initializeSomething() throws IOException {
        log.info("Seeding data!");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Market> markets = objectMapper.readValue(new File("src/main/java/com/yellow/offer/Application/data/initialMarkets.json"), new TypeReference<List<Market>>() {});
        List<Event> events = objectMapper.readValue(new File("src/main/java/com/yellow/offer/Application/data/initialEvents.json"), new TypeReference<List<Event>>() {});

        if(marketRepository.IsSeedNecessary("3way")) {
            markets.forEach(
                    marketRepository::InsertOne
            );
            events.forEach(
                    eventRepository::InsertOne
            );
        }
    }
}
