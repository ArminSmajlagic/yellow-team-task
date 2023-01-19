package com.yellow.offer.Domain.DTO.Offer.Events;

import com.yellow.offer.Domain.DTO.Offer.EventMarket.EventMarketDTO;
import com.yellow.offer.Domain.DTO.Offer.Markets.MarketDTO;
import com.yellow.offer.Domain.Entities.Offer.EventMarket.EventMarket;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Enumeration.Event.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDTO implements Serializable {
    private String id;
    private String name;
    private Date startsAt;
    private EventStatus status;
    private List<EventMarket> markets;
}
