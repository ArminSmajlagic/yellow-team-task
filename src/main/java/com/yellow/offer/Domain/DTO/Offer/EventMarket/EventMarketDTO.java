package com.yellow.offer.Domain.DTO.Offer.EventMarket;

import com.yellow.offer.Domain.DTO.Offer.Markets.MarketDTO;
import com.yellow.offer.Domain.Entities.Offer.EventMarket.EventMarketOutcome;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Enumeration.EventMarket.EventMarketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventMarketDTO {
    private String id;
    private MarketDTO market;
    private EventMarketStatus status;
    private List<EventMarketOutcome> outcomes;

}
