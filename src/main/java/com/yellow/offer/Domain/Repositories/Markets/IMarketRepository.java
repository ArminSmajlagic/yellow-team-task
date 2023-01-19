package com.yellow.offer.Domain.Repositories.Markets;

import com.yellow.offer.Domain.Entities.Offer.Market.Market;
import com.yellow.offer.Domain.Repositories.Common.IRepository;

public interface IMarketRepository extends IRepository<Market, String> {
    Boolean IsSeedNecessary(String id);
}
