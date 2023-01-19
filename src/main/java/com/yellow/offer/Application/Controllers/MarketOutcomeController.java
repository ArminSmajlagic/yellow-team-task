package com.yellow.offer.Application.Controllers;

import com.yellow.offer.Application.Requests.Common.FindOneRequest;
import com.yellow.offer.Application.Requests.Common.SearchRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.UpsertMarketOutcomeRequest;
import com.yellow.offer.Domain.DTO.Offer.Markets.MarketOutcomeDTO;
import com.yellow.offer.Domain.Services.Offer.Markets.MarketOutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//This is demonstration how easy is to use generics to create BaseController (Task doesn't require marketOutcome controller).
@RestController
@RequestMapping("/marketOutcome")
public class MarketOutcomeController extends BaseController<MarketOutcomeDTO, FindOneRequest<String>, SearchRequest, UpsertMarketOutcomeRequest,UpsertMarketOutcomeRequest, String> {
    @Autowired
    public MarketOutcomeController(MarketOutcomeService marketOutcomeService) {
        super(marketOutcomeService);
    }
}
