package com.yellow.offer.Application.Controllers;

import com.yellow.offer.Application.Requests.Offer.Markets.FindOneMarketRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.GetAllMarketsRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.InsertMarketRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.UpdateMarketRequest;
import com.yellow.offer.Domain.DTO.Offer.Markets.MarketDTO;
import com.yellow.offer.Domain.Services.Offer.Markets.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.Serializable;

@RestController
@RequestMapping("/market")
public class MarketController extends BaseController<MarketDTO, FindOneMarketRequest, GetAllMarketsRequest, InsertMarketRequest, UpdateMarketRequest, String> {

    private MarketService service;

    @Autowired
    public MarketController(MarketService service) {
        super(service);
        this.service = service;
    }
    @GetMapping("/apacheKafkaMarketUpdate")
    public ResponseEntity<String> apacheKafkaMarketUpdate() throws IOException {
        service.apacheKafkaUpdateMarkets();
        return ResponseEntity.ok("This endpoint is used to simulate streaming of Market data.");
    }
}
