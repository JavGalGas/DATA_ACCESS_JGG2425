package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.IPassengerDAO;
import com.jgg.catchup.flights_jgg.models.dao.ITicketDAO;
import com.jgg.catchup.flights_jgg.models.entities.Ticket;
import jakarta.validation.constraints.Min;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private ITicketDAO ticketDAO;
    @Autowired
    private IPassengerDAO passengerDAO;

    public Ticket findTicketById(@NotNull @Min(1) @PathVariable(value = "id") int id) {
        Optional<Ticket> ticket = ticketDAO.findById(id);
        return ticket.isPresent() ? ticket.get() : null;
    }

    public Object saveTicket(@Validated Ticket ticket) {
        List<String> errors = new ArrayList<>();

        if (ticket.getId() == null) {
            errors.add("Ticket Id is required");
        }
        if (ticket.getId() != null && ticketDAO.existsById(ticket.getId())) {
            errors.add("Ticket already exists");
        }
        if (ticket.getDateOfBooking() == null) {
            errors.add("Ticket booking date is required");
        }
        if (ticket.getDateOfTravel() == null) {
            errors.add("Ticket travel date is required");
        }
        if (ticket.getFlightCode() == null) {
            errors.add("Ticket flight code is required");
        }
        if (ticket.getPassportno() == null) {
            errors.add("Ticket passport number is required");
        }
        if (ticket.getPassportno() != null && !passengerDAO.existsById(ticket.getPassportno())) {
            errors.add("Passenger does not exist");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        ticketDAO.save(ticket);
        return "Ticket saved.";
    }
}
