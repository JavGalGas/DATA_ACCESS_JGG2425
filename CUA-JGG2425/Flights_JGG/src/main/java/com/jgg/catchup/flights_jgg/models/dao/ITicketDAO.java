package com.jgg.catchup.flights_jgg.models.dao;

import com.jgg.catchup.flights_jgg.models.entities.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ITicketDAO extends CrudRepository<Ticket, Integer> {
    @Query(value="SELECT * FROM can_buy_ticket(:passportno, :flight_code, :travel_date)", nativeQuery = true)
    Optional<Object[]> canBuyTicket(@Param("passportno") String passportno,
                                    @Param("flight_code") String flight_code,
                                    @Param("travel_date") LocalDate travel_date);
    
}
