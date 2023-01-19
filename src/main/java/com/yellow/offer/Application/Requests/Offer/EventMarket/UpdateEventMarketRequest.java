package com.yellow.offer.Application.Requests.Offer.EventMarket;

import com.yellow.offer.Application.Requests.Common.Request;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Enumeration.EventMarket.EventMarketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateEventMarketRequest implements Request {
    @NotNull
    @NotBlank
    @NotEmpty
    private String id;
    @NotNull
    private Market market;
    @NotNull
    private EventMarketStatus status;
}
