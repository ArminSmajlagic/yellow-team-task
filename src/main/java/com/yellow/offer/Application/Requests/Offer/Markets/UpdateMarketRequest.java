package com.yellow.offer.Application.Requests.Offer.Markets;

import com.yellow.offer.Application.Requests.Common.Request;
import com.yellow.offer.Domain.Entities.Offer.Market.MarketOutcome;
import com.yellow.offer.Domain.Enumeration.Market.MarketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateMarketRequest implements Request {

    @NotNull
    @NotBlank
    @NotEmpty
    private String id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String newId;
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @NotNull
    private MarketStatus status;
    @NotNull
    private List<MarketOutcome> outcomes;
}

