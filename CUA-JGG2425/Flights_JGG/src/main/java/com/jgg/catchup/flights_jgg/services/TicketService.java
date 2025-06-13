package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.IPassengerDAO;
import com.jgg.catchup.flights_jgg.models.dao.ITicketDAO;
import com.jgg.catchup.flights_jgg.models.dto.ResultOptionDTO;
import com.jgg.catchup.flights_jgg.models.entities.Ticket;
import jakarta.validation.constraints.Min;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    public static final String TICKET_BOOKING_DATE_REQUIRED = "Ticket booking date is required";
    public static final String TICKET_TRAVEL_DATE_REQUIRED = "Ticket travel date is required";
    public static final String TICKET_FLIGHT_CODE_REQUIRED = "Ticket flight code is required";
    public static final String TICKET_PASSPORT_NUMBER_REQUIRED = "Ticket passport number is required";
    public static final String PASSENGER_DOES_NOT_EXIST = "Passenger does not exist";
    public static final String TICKET_CHECKING_ERROR = "Error while handling ticket checking:";
    public static final String TICKET_DOES_NOT_EXIST = "Ticket does not exist";
    private static final String INCORRECT_FLIGHT_CODE = "Flight code is before current local time";

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

        if (ticket.getDateOfBooking() == null) {
            errors.add(TICKET_BOOKING_DATE_REQUIRED);
        }
        if (ticket.getDateOfTravel() == null) {
            errors.add(TICKET_TRAVEL_DATE_REQUIRED);
        }
        if (ticket.getFlightCode() == null) {
            errors.add(TICKET_FLIGHT_CODE_REQUIRED);
        }
        if (ticket.getPassportno() == null) {
            errors.add(TICKET_PASSPORT_NUMBER_REQUIRED);
        }
        if (ticket.getPassportno() != null && !passengerDAO.existsById(ticket.getPassportno())) {
            errors.add(PASSENGER_DOES_NOT_EXIST);
        }

        Optional<Object> result = ticketDAO.canBuyTicket(ticket.getPassportno(), ticket.getFlightCode(), ticket.getDateOfTravel());
        if (result.isPresent() && result.get() instanceof Object[] && ((Object[]) result.get()).length == 2) {
            Object[] resultArray = (Object[]) result.get();
            boolean canBuy = (boolean) resultArray[0];
            String reason = (String) resultArray[1];
            resultOptionDTO = new ResultOptionDTO(
                    canBuy,
                    reason
            );
            if(!resultOptionDTO.getCan_buy()) {
                errors.add(TICKET_CHECKING_ERROR);
                errors.add(resultOptionDTO.getReason());
            }
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        ticketDAO.save(ticket);
        return "Ticket saved successfully.";
    }

    public Object cancelTicket(String passportno, LocalDate flight_date) {
        List<String> errors = new ArrayList<>();

        if (passportno == null || passportno.isEmpty()) {
            errors.add(TICKET_PASSPORT_NUMBER_REQUIRED);
        }
        if (flight_date == null) {
            errors.add(TICKET_FLIGHT_CODE_REQUIRED);
        }
        if (flight_date.isBefore(LocalDate.now())) {
            errors.add(INCORRECT_FLIGHT_CODE);
        }
        if (passportno != null) {
            Optional<List<Ticket>> tickets = ticketDAO.getTicketsByPassportnoAndDateOfTravel(passportno, flight_date);

            if (tickets.isEmpty()) {
                errors.add(TICKET_DOES_NOT_EXIST);
            }
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        ticketDAO.cancelTicket(passportno, flight_date, LocalDate.now());
        return "Ticket updated successfully.";
    }
}
