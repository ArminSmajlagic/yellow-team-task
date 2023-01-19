package com.yellow.offer.Domain.Services.Offer.Markets;

import com.yellow.offer.Application.Requests.Common.SearchRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.*;
import com.yellow.offer.Domain.DTO.Offer.Markets.MarketDTO;
import com.yellow.offer.Domain.DTO.Offer.Markets.MarketOutcomeDTO;
import com.yellow.offer.Domain.Services.Common.IService;

public interface IMarketService extends IService<MarketDTO, GetAllMarketsRequest, InsertMarketRequest, UpdateMarketRequest, String> {
}
