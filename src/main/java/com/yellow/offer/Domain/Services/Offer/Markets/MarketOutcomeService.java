package com.yellow.offer.Domain.Services.Offer.Markets;

import com.yellow.offer.Application.Requests.Common.SearchRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.GetAllMarketsRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.UpsertMarketOutcomeRequest;
import com.yellow.offer.Domain.DTO.Offer.Markets.MarketOutcomeDTO;
import com.yellow.offer.Domain.Entities.Offer.Market.MarketOutcome;
import com.yellow.offer.Domain.Repositories.Markets.IMarketOutcomeRepository;
import com.yellow.offer.Domain.Services.Common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketOutcomeService extends BaseService<MarketOutcome, SearchRequest, MarketOutcomeDTO, UpsertMarketOutcomeRequest, UpsertMarketOutcomeRequest, String> implements IMarketOutcomeService {
    @Autowired
    public MarketOutcomeService(IMarketOutcomeRepository repository) {
        super();
        super.setRepository(repository);
    }

}
