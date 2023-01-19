package com.yellow.offer.Domain.Entities.Offer.EventMarket;

import com.yellow.offer.Domain.Entities.Common.BaseEntity;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Enumeration.EventMarket.EventMarketStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EventMarket")
public class EventMarket extends BaseEntity implements Serializable {
    private Market market;
    @Column(name = "market_id")
    private String marketId;
    @Column(name = "event_id")
    private String eventId;
    private EventMarketStatus status;
    private List<EventMarketOutcome> outcomes;
}
