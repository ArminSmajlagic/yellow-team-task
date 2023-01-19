package com.yellow.offer.Domain.Services.Offer.Events;

import com.yellow.offer.Application.Requests.Offer.Events.GetAllEventsRequest;
import com.yellow.offer.Application.Requests.Offer.Events.InsertEventRequest;
import com.yellow.offer.Application.Requests.Offer.Events.UpdateEventRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.GetAllMarketsRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.InsertMarketRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.UpdateMarketRequest;
import com.yellow.offer.Domain.DTO.Offer.Events.EventDTO;
import com.yellow.offer.Domain.DTO.Offer.Markets.MarketDTO;
import com.yellow.offer.Domain.Entities.Offer.Event.Event;
import com.yellow.offer.Domain.Services.Common.IService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface IEventService extends IService<EventDTO, GetAllEventsRequest, InsertEventRequest, UpdateEventRequest, String> {
    List<EventDTO> getUpcomingEvents(Date startDate);

}
