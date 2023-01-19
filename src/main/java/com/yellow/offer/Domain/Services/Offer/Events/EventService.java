package com.yellow.offer.Domain.Services.Offer.Events;

import com.yellow.offer.Application.Requests.Offer.Events.GetAllEventsRequest;
import com.yellow.offer.Application.Requests.Offer.Events.InsertEventRequest;
import com.yellow.offer.Application.Requests.Offer.Events.UpdateEventRequest;
import com.yellow.offer.Domain.DTO.Offer.Events.EventDTO;
import com.yellow.offer.Domain.Entities.Offer.Event.Event;
import com.yellow.offer.Domain.Repositories.Events.IEventRepository;
import com.yellow.offer.Domain.Services.Common.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Service
public class EventService extends BaseService<Event, GetAllEventsRequest, EventDTO, InsertEventRequest, UpdateEventRequest, String> implements IEventService {
    private IEventRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    public EventService(IEventRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        super();
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        super.setRepository(repository);
    }

    @Override
    public List<EventDTO> getUpcomingEvents(Date startDate) {
        var result = repository.getUpcomingEvents(startDate);
        List<EventDTO> mappedResult = result.stream().map(e -> modelMapper.map(e, EventDTO.class)).toList();
        return mappedResult;
    }

    public void apacheKafkaEventUpdate() throws IOException {

        //Reading update json data
        BufferedReader br = new BufferedReader(new FileReader(new File("src/main/java/com/yellow/offer/Application/data/updateEvents.json")));

        //Writing it to the message
        String message = br.readLine();

        //Producing the message onto eventTopic
        kafkaTemplate.send("eventTopic", message);
    }
}
