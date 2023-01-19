package com.yellow.offer.Application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yellow.offer.Application.Configuration.DependencyConfiguration;
import com.yellow.offer.Domain.Entities.Offer.Event.Event;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Repositories.Events.IEventRepository;
import com.yellow.offer.Domain.Repositories.Markets.IMarketRepository;
import com.yellow.offer.Infrastructure.Repositories.Offer.Events.EventRepository;
import com.yellow.offer.Infrastructure.Repositories.Offer.Markets.MarketRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableCaching
@Slf4j
public class OfferApplication {
    public static void main(String[] args) {
        TemplateMethod(args);
    }

    //Template method (behavioral) design pattern
    private static void TemplateMethod(String[] args) {
        StartApplication(args);
        SeedData();
    }

    private static void StartApplication(String[] args) {
        SpringApplication.run(OfferApplication.class, args);
        log.info("Starting application");
    }
    private static void SeedData() {
        log.info("Seeding logic has been moved to com.yellow.offer.Application.data.ApplicationSeed");
    }
}
