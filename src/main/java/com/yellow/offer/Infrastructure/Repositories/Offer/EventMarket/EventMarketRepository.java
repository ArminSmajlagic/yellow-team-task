package com.yellow.offer.Infrastructure.Repositories.Offer.EventMarket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yellow.offer.Domain.Entities.Offer.Event.Event;
import com.yellow.offer.Domain.Entities.Offer.EventMarket.EventMarket;
import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Repositories.EventMarket.IEventMarketOutcomeRepository;
import com.yellow.offer.Domain.Repositories.EventMarket.IEventMarketRepository;
import com.yellow.offer.Domain.Repositories.Markets.IMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventMarketRepository implements IEventMarketRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IEventMarketOutcomeRepository eventMarketOutcomeRepository;
    @Autowired
    private IMarketRepository marketRepository;

    @Override
    @Cacheable(value = "eventMarket")
    public List<EventMarket> GetAll() {
        //If Event market is inactive it shouldn’t be delivered to the client.
        String sql = "SELECT * FROM EventMarket WHERE status = 'ACTIVE'";

        var result = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(EventMarket.class));

        var eventMarketOutcomes = eventMarketOutcomeRepository.GetAll();
        var markets = marketRepository.GetAll();

        //Application needs to deliver events with their belonging event markets and event market outcomes
        result.forEach(eventMarket->{
            eventMarket.setMarket(markets.stream().filter(m->m.getId().equals(eventMarket.getMarketId())).findFirst().orElse(null));
            eventMarket.setOutcomes(eventMarketOutcomes.stream().filter(emo->emo.getEventMarketId().equals(eventMarket.getId())).collect(Collectors.toList()));
        });

        //If the market can’t be found then the
        //state of the system is considered invalid
        //and this event market shouldn’t be
        //delivered to the client.
        result.stream().filter(eventMarket -> eventMarket.getMarket() != null).collect(Collectors.toList());

        return result;
    }
    @Override
    public EventMarket GetById(String id) {
        //If the Event is INACTIVE then it shouldn’t be visible to the client.
        String query = "SELECT * FROM EventMarket WHERE id = ? AND status = 'ACTIVE'";
        try {
            var result = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(EventMarket.class), id);
            return result;
        }catch (EmptyResultDataAccessException exception){
            return null;
        }
    }
    @Override
    @CacheEvict(value = "eventMarket", allEntries=true)
    public Integer InsertOne(EventMarket eventMarket) {

        String sql = """
        INSERT INTO EventMarket (id, market_id, event_id, status) VALUES (?,?,?,?)
        """;

        var queryResult = jdbcTemplate.update(sql,eventMarket.getId(), eventMarket.getMarketId(),eventMarket.getEventId(), eventMarket.getStatus().toString());

        List<Integer> eventMarketOutcomeResults = new ArrayList<>();


        eventMarket.getOutcomes().forEach(eventMarketOutcome -> {
            eventMarketOutcome.setEventMarketId(eventMarket.getId());
            eventMarketOutcomeResults.add(eventMarketOutcomeRepository.InsertOne(eventMarketOutcome));
        });


        if(eventMarketOutcomeResults.contains(0)){
            //If something didn’t get inserted correctly rollback the operation
            DeleteOne(eventMarket.getId());
            eventMarket.getOutcomes().forEach(outcome -> {
                eventMarketOutcomeRepository.DeleteOne(outcome.getId());
            });
            return 0;

        }else{
            return queryResult;
        }
    }
    @Override
    @CacheEvict(value = "eventMarket", allEntries=true)
    public EventMarket PatchOne(EventMarket eventMarket) {
        String sql = """
        UPDATE EventMarket
        SET market_id = ?, event_id = ?, status = ?, updatedAt = NOW()
        WHERE id =?
        """;

        var queryResult = jdbcTemplate.update(sql,eventMarket.getMarketId(),eventMarket.getEventId(), eventMarket.getStatus().toString(),eventMarket.getId());

        eventMarket.getOutcomes().forEach(eventMarketOutcome -> {
            eventMarketOutcome.setEventMarketId(eventMarket.getId());
            eventMarketOutcomeRepository.PatchOne(eventMarketOutcome);
        });

        if(queryResult>0) {
            var result = GetById(eventMarket.getId());
            return result;
        }
        return null;
    }
    @Override
    @CacheEvict(value = "eventMarket", allEntries=true)
    public EventMarket DeleteOne(String id) {
        var result = GetById(id);

        String sql = "DELETE FROM EventMarket WHERE id = ?";

        var queryResult = jdbcTemplate.update(sql, id);

        if(queryResult>0){
            return result;
        }
        return null;
    }
}
