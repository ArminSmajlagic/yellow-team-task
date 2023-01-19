package com.yellow.offer.Application.Controllers;

import com.yellow.offer.Application.Exceptions.InternalServerErrorException;
import com.yellow.offer.Application.Requests.Offer.Events.*;
import com.yellow.offer.Application.Requests.Offer.Markets.FindOneMarketRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.GetAllMarketsRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.InsertMarketRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.UpdateMarketRequest;
import com.yellow.offer.Domain.DTO.Offer.Events.EventDTO;
import com.yellow.offer.Domain.DTO.Offer.Markets.MarketDTO;
import com.yellow.offer.Domain.Services.Offer.Events.EventService;
import com.yellow.offer.Domain.Services.Offer.Markets.MarketService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/event")
@Slf4j
public class EventController extends BaseController<EventDTO, FindOneEventRequest, GetAllEventsRequest, InsertEventRequest, UpdateEventRequest, String> {
    private EventService service;

    @Autowired
    public EventController(EventService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/filterEventsByStartDate")
    public List<EventDTO> filterEventsByStartDate(@Valid FindByStartsAtRequest request)
            throws InternalServerErrorException, NotFoundException {
        try{
            Date date = Date.valueOf(request.getStartsAt());
            var result = service.getUpcomingEvents(date);
            if (result == null)
                throw new NotFoundException("Seems like no events were found!");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause(), e.getStackTrace());
            throw new InternalServerErrorException("Service seems to be unavailable at the moment. Please try again later.");
        }
    }

    @GetMapping("/apacheKafkaEventUpdate")
    public ResponseEntity<String> apacheKafkaMarketUpdate() throws IOException {
        service.apacheKafkaEventUpdate();
        return ResponseEntity.ok("This endpoint is used to simulate streaming of Event data.");
    }
}
