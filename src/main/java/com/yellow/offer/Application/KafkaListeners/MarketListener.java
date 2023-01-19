package com.yellow.offer.Application.KafkaListeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Infrastructure.Repositories.Offer.Markets.MarketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MarketListener {
    @Autowired
    private MarketRepository marketRepository;
    @KafkaListener(topics = "marketTopic", groupId = "marketGroup")
    public void listen(String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        data = data.replaceAll("\\\\","");// Removing backslashes from message
        data = data.substring(1,data.length()-1);// Removing double quotes from message

        Market market = objectMapper.readValue(data, Market.class); // Mapping json to market

        var result = marketRepository.GetById(market.getId()); // Trying to get market by id

        // If the market exists I perform update, otherwise I perform insert
        // It is impossible to perform update on table market column id without providing the old id
        // The given data has different market id & outcome id so neither one can be updated
        if(result == null) {
            marketRepository.InsertOne(market);
        }else{
            marketRepository.PatchOne(market);
        }
    }
}
