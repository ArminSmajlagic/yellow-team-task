package com.yellow.offer.Application.KafkaListeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yellow.offer.Domain.Entities.Offer.Event.Event;
import com.yellow.offer.Domain.Entities.Offer.EventMarket.EventMarket;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Infrastructure.Repositories.Offer.Events.EventRepository;
import com.yellow.offer.Infrastructure.Repositories.Offer.Markets.MarketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class EventListener {
    @Autowired
    private EventRepository eventRepository;
    @KafkaListener(topics = "eventTopic", groupId = "eventGroup")
    public void listen(String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        data = data.replaceAll("\\\\",""); // Removing backslashes from message
        data = data.substring(1,data.length()-1); // Removing double quotes from message

        Event mappedEvent = objectMapper.readValue(data, Event.class); // Mapping json to event

        var result = eventRepository.GetById(mappedEvent.getId());

        // If the event exists I perform update, otherwise I perform insert
        // It is impossible to perform update on table event column id without providing the old id
        // Market id might be the same, but then we might lose data in other events if we update corresponding market & eventMarket
        // Same goes for eventMarketOutcome
        if(result == null) {
            eventRepository.InsertOne(mappedEvent);
        }else{
            eventRepository.PatchOne(mappedEvent);
        }
    }
}