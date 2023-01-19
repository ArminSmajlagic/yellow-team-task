package com.yellow.offer.Infrastructure.Repositories.Offer.Markets;

import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Repositories.Markets.IMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarketRepository implements IMarketRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private MarketOutcomeRepository repository;
    @Autowired
    public MarketRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Cacheable(value = "markets")
    @Override
    public List<Market> GetAll() {
        //If market is inactive it shouldn’t be delivered to the client.
        String sql = "SELECT * FROM Market WHERE status = 'ACTIVE'";

        var result = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Market.class));

        var outcomes = repository.GetAll();

        result.forEach(market->{
            market.setOutcomes(outcomes.stream().filter(o->o.getMarket_id().equals(market.getId())).collect(Collectors.toList()));
        });

        return result;
    }
    @Cacheable(value = "market")
    @Override
    public Market GetById(String id) {
        //If market outcome is inactive it shouldn’t be delivered to the client.
        String query = "SELECT * FROM Market WHERE id = ? AND status = 'ACTIVE'";
        try {
            var result = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Market.class), id);
            return result;
        }catch (EmptyResultDataAccessException exception){
            return null;
        }
    }
    @Override
    @CacheEvict(value = "markets", allEntries=true)
    public Integer InsertOne(Market request) {

        String sql = """
        INSERT INTO Market (id, name, status) VALUES (?,?,?)
        """;

        var queryResult = jdbcTemplate.update(sql,request.getId(), request.getName(), request.getStatus().toString());

        List<Integer> results = new ArrayList<>();

        request.getOutcomes().forEach(outcome -> {
            outcome.setMarket_id(request.getId());
            results.add(repository.InsertOne(outcome));
        });

        if(results.contains(0)){
            DeleteOne(request.getId());
            request.getOutcomes().forEach(outcome -> {
                repository.DeleteOne(outcome.getId());
            });
            return 0;

        }else{
            return queryResult;
        }
    }
    @Override
    @CacheEvict(value = "markets", allEntries=true)
    public Market PatchOne(Market request) {
        String sql = """
            UPDATE Market
            SET name = ?, status = ?, updatedAt = NOW()
            WHERE id = ?
            """;
        var queryResult = jdbcTemplate.update(sql,request.getName(), request.getStatus().toString(), request.getId());

        request.getOutcomes().forEach(outcome -> {
            outcome.setMarket_id(request.getId());
            repository.PatchOne(outcome);
        });

        if(queryResult>0) {
            var result = GetById(request.getId());
            return result;
        }
        return null;
    }
    @Override
    @CacheEvict(value = "markets", allEntries=true)
    public Market DeleteOne(String id) {
        var result = GetById(id);

        String sql = "DELETE FROM Market WHERE id = ?";

        var queryResult = jdbcTemplate.update(sql, id);

        //TODO delete children

        if(queryResult>0){
            return result;
        }
        return null;
    }

    @Override
    public Boolean IsSeedNecessary(String id) {
        var result = GetById(id);

        return result == null;
    }

}
