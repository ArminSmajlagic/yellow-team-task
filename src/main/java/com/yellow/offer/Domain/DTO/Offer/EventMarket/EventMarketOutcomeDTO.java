package com.yellow.offer.Domain.DTO.Offer.EventMarket;

import com.yellow.offer.Domain.DTO.Offer.Markets.MarketOutcomeDTO;
import com.yellow.offer.Domain.Entities.Offer.Market.MarketOutcome;
import com.yellow.offer.Domain.Enumeration.EventMarket.EventMarketOutcomeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventMarketOutcomeDTO {
    private String id;
    private MarketOutcome outcome;
    private EventMarketOutcomeStatus status;
    private Double odd;

}
