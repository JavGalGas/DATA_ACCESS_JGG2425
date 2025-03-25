package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.IPassengerDAO;
import com.jgg.catchup.flights_jgg.models.dao.ITicketDAO;
import com.jgg.catchup.flights_jgg.models.entities.Ticket;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    public ResponseEntity<Ticket> findTicketById(@NotNull @PathVariable(value = "id") int id) {
        Optional<Ticket> ticket = ticketDAO.findById(id);
        return ticket.isPresent() ? ResponseEntity.ok(ticket.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> saveTicket(Ticket ticket) {
        List<String> errors = new ArrayList<>();

        if (ticket.getId() == null) {
            errors.add("Associated Ticket's Id not found");
        }
        if (ticket.getId() != null && ticketDAO.existsById(ticket.getId())) {
            errors.add("Associated Ticket already exists");
        }
        if (ticket.getDateOfBooking() == null) {
            errors.add("Associated Ticket's booking date not found");
        }
        if (ticket.getDateOfTravel() == null) {
            errors.add("Associated Ticket's travel date not found");
        }
        if (ticket.getFlightCode() == null) {
            errors.add("Associated Ticket's flight code not found");
        }
        if (ticket.getPassportno() == null) {
            errors.add("Associated Ticket's passport number not found");
        }
        if (ticket.getPassportno() != null && !passengerDAO.existsById(ticket.getPassportno().getPassportno())) {
            errors.add("Associated Passenger does not exist");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
        }

        ticketDAO.save(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ticket saved successfully.");
    }
}
