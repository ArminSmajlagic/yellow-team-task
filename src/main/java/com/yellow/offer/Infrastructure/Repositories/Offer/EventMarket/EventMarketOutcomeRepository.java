package com.yellow.offer.Infrastructure.Repositories.Offer.EventMarket;

import com.yellow.offer.Domain.Entities.Offer.EventMarket.EventMarket;
import com.yellow.offer.Domain.Entities.Offer.EventMarket.EventMarketOutcome;
import com.yellow.offer.Domain.Entities.Offer.Market.MarketOutcome;
import com.yellow.offer.Domain.Repositories.EventMarket.IEventMarketOutcomeRepository;
import com.yellow.offer.Domain.Repositories.Markets.IMarketOutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventMarketOutcomeRepository implements IEventMarketOutcomeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IMarketOutcomeRepository marketOutcomeRepository;

    @Override
    @Cacheable(value = "eventMarketOutcome")
    public List<EventMarketOutcome> GetAll() {
        String sql = "SELECT * FROM EventMarketOutcome";

        var result = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(EventMarketOutcome.class));

        var outcomes = marketOutcomeRepository.GetAll();

        //Application needs to deliver events with their belonging event markets and event market outcomes
        result.forEach(eventMarketOutcome -> {
            eventMarketOutcome.setOutcome(outcomes.stream().filter(o-> o.getId().equals(eventMarketOutcome.getOutcomeId())).findFirst().orElse(null));;
        });

        //If the outcome can’t be found then the
        //state of the system is considered invalid
        //and this event market outcome
        //shouldn’t be delivered to the client.
        result.stream().filter(eventMarketOutcome -> eventMarketOutcome.getOutcome()!= null).collect(Collectors.toList());

        return result;
    }

    @Override
    public EventMarketOutcome GetById(String id) {
        //If the Event is INACTIVE then it shouldn’t be visible to the client.
        String query = "SELECT * FROM EventMarketOutcome WHERE id = ? AND status = 'ACTIVE'";
        try {
            var result = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(EventMarketOutcome.class), id);
            return result;
        }catch (EmptyResultDataAccessException exception){
            return null;
        }
    }

    @Override
    @CacheEvict(value = "eventMarketOutcome", allEntries=true)
    public Integer InsertOne(EventMarketOutcome request) {
        String sql = """
        INSERT INTO EventMarketOutcome (id, event_market_id, outcome_id, odd, status) VALUES (?,?,?,?,?)
        """;
        var queryResult = jdbcTemplate.update(sql,request.getId(),request.getEventMarketId(), request.getOutcomeId(),request.getOdd(), request.getStatus().toString());
        return queryResult;
    }

    @Override
    @CacheEvict(value = "eventMarketOutcome", allEntries=true)
    public EventMarketOutcome PatchOne(EventMarketOutcome request) {
        String sql = """
        UPDATE EventMarketOutcome
        SET event_market_id = ?, outcome_id  = ?, odd  = ?, status = ?, updatedAt = NOW()
        WHERE id = ?
        """;

        var queryResult = jdbcTemplate.update(sql,request.getEventMarketId(), request.getOutcomeId(),request.getOdd(), request.getStatus().toString(),request.getId());

        if(queryResult>0) {
            var result = GetById(request.getId());
            return result;
        }
        return null;
    }
    @Override
    @CacheEvict(value = "eventMarketOutcome", allEntries=true)
    public EventMarketOutcome DeleteOne(String id) {
        var result = GetById(id);

        String sql = "DELETE FROM EventMarketOutcome WHERE id = ?";

        var queryResult = jdbcTemplate.update(sql, id);

        if(queryResult>0){
            return result;
        }
        return null;
    }
}
