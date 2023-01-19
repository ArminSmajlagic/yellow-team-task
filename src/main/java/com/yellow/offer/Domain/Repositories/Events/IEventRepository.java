package com.yellow.offer.Domain.Repositories.Events;

import com.yellow.offer.Domain.Entities.Offer.Event.Event;
import com.yellow.offer.Domain.Repositories.Common.IRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IEventRepository extends IRepository<Event, String> {
    List<Event> getUpcomingEvents(Date startDate);
}
