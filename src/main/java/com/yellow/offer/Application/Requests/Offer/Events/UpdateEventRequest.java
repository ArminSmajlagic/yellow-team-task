package com.yellow.offer.Application.Requests.Offer.Events;

import com.yellow.offer.Application.Requests.Common.Request;
import com.yellow.offer.Domain.Entities.Offer.EventMarket.EventMarket;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Enumeration.Event.EventStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateEventRequest implements Request {
    @NotNull
    @NotBlank
    @NotEmpty
    private String id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @NotNull
    private LocalDateTime startsAt;
    @NotNull
    private EventStatus status;
    @NotNull
    private List<EventMarket> markets;
}
