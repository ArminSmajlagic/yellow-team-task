package com.yellow.offer.Domain.Services.Offer.Markets;

import com.yellow.offer.Application.Requests.Offer.Markets.GetAllMarketsRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.InsertMarketRequest;
import com.yellow.offer.Application.Requests.Offer.Markets.UpdateMarketRequest;
import com.yellow.offer.Domain.DTO.Offer.Markets.MarketDTO;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Repositories.Markets.IMarketRepository;
import com.yellow.offer.Domain.Services.Common.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Slf4j
public class MarketService extends BaseService<Market, GetAllMarketsRequest, MarketDTO, InsertMarketRequest, UpdateMarketRequest, String> implements IMarketService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    public MarketService(IMarketRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        super();
        this.kafkaTemplate = kafkaTemplate;
        super.setRepository(repository);
    }
    public void apacheKafkaUpdateMarkets() throws IOException {
        //Reading update json data
        BufferedReader br = new BufferedReader(new FileReader(new File("src/main/java/com/yellow/offer/Application/data/updateMarkets.json")));

        //Writing it to the message
        String message = br.readLine();

        //Producing the message onto marketTopic
        kafkaTemplate.send("marketTopic", message);
    }
}
