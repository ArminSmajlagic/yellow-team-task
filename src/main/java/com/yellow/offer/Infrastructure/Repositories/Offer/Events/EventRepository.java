package com.yellow.offer.Infrastructure.Repositories.Offer.Events;

import com.yellow.offer.Domain.Entities.Offer.Event.Event;
import com.yellow.offer.Domain.Repositories.EventMarket.IEventMarketRepository;
import com.yellow.offer.Domain.Repositories.Events.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventRepository implements IEventRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Autowired
    private IEventMarketRepository eventMarketRepository;

    @Cacheable(value = "events")
    @Override
    public List<Event> GetAll() {
        //If the current datetime is greater than
        //startsAt then the event shouldn’t be
        //visible to the client.
        //If the Event is INACTIVE then it shouldn’t be visible to the client.
        String sql = "SELECT * FROM Event WHERE status = 'ACTIVE' AND startsAt > NOW()";

        var result = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Event.class));

        var eventMarkets = eventMarketRepository.GetAll();

        //Application needs to deliver events with their belonging event markets and event market outcomes
        result.forEach(event->{
            event.setMarkets(eventMarkets.stream().filter(em->em.getEventId().equals(event.getId())).collect(Collectors.toList()));
        });

        return result;
    }

    @Cacheable(value = "event")
    @Override
    public Event GetById(String id) {
        //If the current datetime is greater than
        //startsAt then the event shouldn’t be
        //visible to the client.
        //If the Event is INACTIVE then it shouldn’t be visible to the client.
        String query = "SELECT * FROM Event WHERE id = ? AND status = 'ACTIVE' AND startsAt > NOW()";
        try {
            var result = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Event.class), id);

            var eventMarkets = eventMarketRepository.GetAll();
            eventMarkets.forEach(em->{
                if(em.getEventId().equals(result.getId())){
                    result.getMarkets().add(em);
                }
            });

            return result;
        }catch (EmptyResultDataAccessException exception){
            return null;
        }
    }
    @Override
    @CacheEvict(value = "events", allEntries=true)
    public Integer InsertOne(Event event) {

        String sql = """
        INSERT INTO Event (id, name,startsAt, status) VALUES (?,?,?,?)
        """;

        var queryResult = jdbcTemplate.update(sql,event.getId(), event.getName(),event.getStartsAt(), event.getStatus().toString());

        List<Integer> eventMarketResults = new ArrayList<>();

        event.getMarkets().forEach(eventMarket -> {
            eventMarket.setEventId(event.getId());
            eventMarket.getOutcomes().forEach(eventMarketOutcome -> {
                eventMarketOutcome.setEventMarketId(eventMarket.getId());
            });

            eventMarketResults.add(eventMarketRepository.InsertOne(eventMarket));
        });


        if(eventMarketResults.contains(0)){
            //If something didn’t get inserted correctly rollback the operation
            DeleteOne(event.getId());
            event.getMarkets().forEach(eventMarket -> {
                eventMarketRepository.DeleteOne(eventMarket.getId());
             });
            return 0;
        }else{
            return queryResult;
        }
    }
    @Override
    @CacheEvict(value = "events", allEntries=true)
    public Event PatchOne(Event event) {
        String sql = """
            UPDATE Event
            SET name = ?, status = ?, updatedAt = NOW()
            WHERE id = ?
            """;
        var queryResult = jdbcTemplate.update(sql,event.getName(), event.getStatus().toString(), event.getId());


        event.getMarkets().forEach(eventMarket -> {
            eventMarket.setEventId(event.getId());
            eventMarket.getOutcomes().forEach(eventMarketOutcome -> {
                eventMarketOutcome.setEventMarketId(eventMarket.getId());
            });

            eventMarketRepository.PatchOne(eventMarket);
        });

        if(queryResult>0) {
            var result = GetById(event.getId());
            return result;
        }
        return null;
    }
    @Override
    @CacheEvict(value = "events", allEntries=true)
    public Event DeleteOne(String id) {
        var result = GetById(id);

        String sql = "DELETE FROM Event WHERE id = ?";

        var queryResult = jdbcTemplate.update(sql, id);

        if(queryResult>0){
            return result;
        }
        return null;
    }
    @Override
    @Cacheable(value = "upcomingEvents")
    public List<Event> getUpcomingEvents(Date startDate) {

        //If the Event is INACTIVE then it shouldn’t be visible to the client.
       String sql = "SELECT * FROM Event WHERE startsAt >=? AND status = 'ACTIVE'";

        var statement = new PreparedStatementSetter(){
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setDate(1, startDate);
            }
        };

        var result = jdbcTemplate.query(sql,statement, BeanPropertyRowMapper.newInstance(Event.class));

        var eventMarkets = eventMarketRepository.GetAll();

        result.forEach(event->{
            event.setMarkets(eventMarkets.stream().filter(em->em.getEventId().equals(event.getId())).collect(Collectors.toList()));
        });

        return result;

    }
}
