package com.yellow.offer.Domain.Entities.Offer.Event;

import com.yellow.offer.Domain.Entities.Common.BaseEntity;
import com.yellow.offer.Domain.Entities.Offer.EventMarket.EventMarket;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Enumeration.Event.EventStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Event")
public class Event extends BaseEntity implements Serializable {
    private String name;
    private Date startsAt; //If the current datetime is greater than this value then the event should not be visible to the client. Always provided in UTC.
    private EventStatus status;
    private List<EventMarket> markets;
}
