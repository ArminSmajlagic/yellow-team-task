package com.yellow.offer.Domain.Entities.Offer.Market;

import com.yellow.offer.Domain.Entities.Common.BaseEntity;
import com.yellow.offer.Domain.Enumeration.Market.MarketOutcomeStatus;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MarketOutcome")
public class MarketOutcome extends BaseEntity implements Serializable {
    private String name;
    private String market_id;
    private MarketOutcomeStatus status;

    public MarketOutcome(String id, String name, String market_id, MarketOutcomeStatus status) {
        super(id);
    }
}
