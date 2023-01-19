package com.yellow.offer.Domain.DTO.Offer.Markets;

import com.yellow.offer.Domain.Entities.Offer.Market.MarketOutcome;
import com.yellow.offer.Domain.Enumeration.Market.MarketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MarketDTO implements Serializable {
    private String id;
    private String name;
    private MarketStatus status;
    private List<MarketOutcome> outcomes;
}
