package com.yellow.offer.Infrastructure.Repositories.Offer.Markets;

import com.yellow.offer.Domain.Entities.Offer.Market.MarketOutcome;
import com.yellow.offer.Domain.Repositories.Markets.IMarketOutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MarketOutcomeRepository implements IMarketOutcomeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    @Cacheable(value = "marketOutcomes")
    public List<MarketOutcome> GetAll() {
        //If market outcome is inactive it shouldn’t be delivered to the client.
        String sql = "SELECT * FROM MarketOutcome WHERE status = 'ACTIVE'";

        var result = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MarketOutcome.class));

        return result;
    }
    @Override
    @Cacheable(value = "marketOutcome")
    public MarketOutcome GetById(String id) {
        //If market outcome is inactive it shouldn’t be delivered to the client.
        String query = "SELECT * FROM MarketOutcome WHERE id = ? AND status = 'ACTIVE'";
        try {
            var result = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(MarketOutcome.class), id);
            return result;
        }catch (EmptyResultDataAccessException exception){
            return null;
        }
    }
    @Override
    @CacheEvict(value = "marketOutcomes", allEntries=true)
    public Integer InsertOne(MarketOutcome request) {
        String sql = """
        INSERT INTO MarketOutcome (id,market_id, name, status) VALUES (?,?,?,?)
        """;

        var queryResult = jdbcTemplate.update(sql,request.getId(),request.getMarket_id(), request.getName(), request.getStatus().toString());

        return queryResult;
    }
    @Override
    @CacheEvict(value = "marketOutcomes", allEntries=true)
    public MarketOutcome PatchOne(MarketOutcome request) {
        String sql = """
                UPDATE MarketOutcome
                SET name = ?,market_id = ?, status = ?, updatedAt = NOW()
                WHERE id = ?
                """;
        var queryResult = jdbcTemplate.update(sql,request.getName(),request.getMarket_id(), request.getStatus().toString(), request.getId());

        if(queryResult>0) {
            var result = GetById(request.getId());
            return result;
        }
        return null;
    }
    @Override
    @CacheEvict(value = "marketOutcomes", allEntries=true)
    public MarketOutcome DeleteOne(String id) {
        var result = GetById(id);

        String sql = "DELETE FROM MarketOutcome WHERE id = ?";

        var queryResult = jdbcTemplate.update(sql, id);

        if(queryResult>0){
            return result;
        }

        return null;
    }
}
