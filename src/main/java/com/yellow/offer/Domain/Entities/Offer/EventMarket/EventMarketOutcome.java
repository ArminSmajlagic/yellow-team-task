package com.yellow.offer.Domain.Entities.Offer.EventMarket;

import com.yellow.offer.Domain.Entities.Common.BaseEntity;
import com.yellow.offer.Domain.Entities.Offer.Market.MarketOutcome;
import com.yellow.offer.Domain.Enumeration.EventMarket.EventMarketOutcomeStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
@Table(name = "EventMarketOutcome")
public class EventMarketOutcome extends BaseEntity implements Serializable {
    private MarketOutcome outcome;
    @Column(name = "outcome_id")
    private String outcomeId;
    @Column(name = "event_market_id")
    private String eventMarketId;
    private EventMarketOutcomeStatus status;
    private Double odd;
}
