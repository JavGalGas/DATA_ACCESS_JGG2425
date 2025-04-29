package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.IPassengerDAO;
import com.jgg.catchup.flights_jgg.models.dao.ITicketDAO;
import com.jgg.catchup.flights_jgg.models.dto.ResultOptionDTO;
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

    public static final String TICKET_ID_IS_REQUIRED = "Ticket Id is required";
    public static final String TICKET_ALREADY_EXISTS = "Ticket already exists";
    public static final String TICKET_BOOKING_DATE_IS_REQUIRED = "Ticket booking date is required";
    public static final String TICKET_TRAVEL_DATE_IS_REQUIRED = "Ticket travel date is required";
    public static final String TICKET_FLIGHT_CODE_IS_REQUIRED = "Ticket flight code is required";
    public static final String TICKET_PASSPORT_NUMBER_IS_REQUIRED = "Ticket passport number is required";
    public static final String PASSENGER_DOES_NOT_EXIST = "Passenger does not exist";
    public static final String ERROR_WHILE_HANDLING_TICKET_CHECKING = "Error while handling ticket checking:";

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
        ResultOptionDTO resultOptionDTO;

        if (ticket.getId() == null) {
            errors.add(TICKET_ID_IS_REQUIRED);
        }
        if (ticket.getId() != null && ticketDAO.existsById(ticket.getId())) {
            errors.add(TICKET_ALREADY_EXISTS);
        }
        if (ticket.getDateOfBooking() == null) {
            errors.add(TICKET_BOOKING_DATE_IS_REQUIRED);
        }
        if (ticket.getDateOfTravel() == null) {
            errors.add(TICKET_TRAVEL_DATE_IS_REQUIRED);
        }
        if (ticket.getFlightCode() == null) {
            errors.add(TICKET_FLIGHT_CODE_IS_REQUIRED);
        }
        if (ticket.getPassportno() == null) {
            errors.add(TICKET_PASSPORT_NUMBER_IS_REQUIRED);
        }
        if (ticket.getPassportno() != null && !passengerDAO.existsById(ticket.getPassportno())) {
            errors.add(PASSENGER_DOES_NOT_EXIST);
        }

        Optional<Object[]> result = ticketDAO.canBuyTicket(ticket.getPassportno(), ticket.getFlightCode(), ticket.getDateOfTravel());
        if (result.isPresent()) {
            resultOptionDTO = new ResultOptionDTO(
                    (boolean) result.get()[0],
                    (String) result.get()[1]
            );
            if(!resultOptionDTO.getCan_buy()) {
                errors.add(ERROR_WHILE_HANDLING_TICKET_CHECKING);
                errors.add(resultOptionDTO.getReason());
            }
        }

        if (!errors.isEmpty() && errors.size() <= 2) {
            return errors;
        }



        ticketDAO.save(ticket);
        return "Ticket saved.";
    }
}
