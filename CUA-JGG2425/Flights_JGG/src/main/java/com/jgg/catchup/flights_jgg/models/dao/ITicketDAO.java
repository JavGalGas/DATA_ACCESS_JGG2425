package com.jgg.catchup.flights_jgg.models.dao;

import com.jgg.catchup.flights_jgg.models.entities.Ticket;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ITicketDAO extends CrudRepository<Ticket, Integer> {
    @Query(value="SELECT can_buy, reason FROM can_buy_ticket(:passportno, :flight_code, :travel_date)", nativeQuery = true)
    Optional<Object> canBuyTicket(@Param("passportno") String passportno,
                                    @Param("flight_code") String flight_code,
                                    @Param("travel_date") LocalDate travel_date);

    @Query(value = "UPDATE public.tickets SET date_of_cancellation = :cancellation_date WHERE passportno = :passportno AND date_of_travel = :flight_date", nativeQuery = true)
    void cancelTicket(@Param("passportno") String passportno,
                      @Param("flight_date") LocalDate flight_date,
                      @Param("cancellation_date") LocalDate cancellation_date);

    Optional<List<Ticket>> getTicketsByPassportnoAndDateOfTravel(@Size(max = 10) @NotNull @Pattern(regexp = "^[A-Z0-9]{6,9}$", message = "Passport number must be in a valid format") String passportno, @NotNull LocalDate dateOfTravel);
}
