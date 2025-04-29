package com.jgg.catchup.flights_jgg.models.dao;

import com.jgg.catchup.flights_jgg.models.entities.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketDAO extends CrudRepository<Ticket, Integer> {
    @Query()
    public Object[] canBuyTicket(Ticket ticket);
    
}
