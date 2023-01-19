package com.yellow.offer.Domain.Services.Offer.Markets;

import com.yellow.offer.Application.Requests.Common.SearchRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.UpsertMarketOutcomeRequest;
import com.yellow.offer.Domain.DTO.Offer.Markets.MarketOutcomeDTO;
import com.yellow.offer.Domain.Services.Common.IService;

public interface IMarketOutcomeService extends IService<MarketOutcomeDTO, SearchRequest, UpsertMarketOutcomeRequest,UpsertMarketOutcomeRequest, String> {
}
