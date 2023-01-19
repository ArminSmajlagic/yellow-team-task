package com.yellow.offer.Domain.Entities.Offer.Market;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.yellow.offer.Domain.Entities.Common.BaseEntity;
import com.yellow.offer.Domain.Enumeration.Market.MarketStatus;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Market")
public class Market extends BaseEntity  implements Serializable {
    private String name;
    private MarketStatus status;
    private List<MarketOutcome> outcomes;

    public Market(String json){
        JsonDeserializer<Market> parser = new JsonDeserializer<>();
        name = json;
    }
}
