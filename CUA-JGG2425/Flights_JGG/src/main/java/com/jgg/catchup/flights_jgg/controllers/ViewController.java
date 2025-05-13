package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import com.jgg.catchup.flights_jgg.models.entities.Passenger;
import com.jgg.catchup.flights_jgg.models.entities.Ticket;
import com.jgg.catchup.flights_jgg.services.FlightService;
import com.jgg.catchup.flights_jgg.services.PassengerService;
import com.jgg.catchup.flights_jgg.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    @Autowired
    private FlightService flightService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private TicketService ticketService;

    @GetMapping("/ticket_sale")
    public String index(
            Model model,
            @RequestParam(value = "selected_origin", required = false) String origin,
            @RequestParam(value = "selected_destination", required = false) String destination,
            @RequestParam(value = "passportno", required = false) String passportno,
            @RequestParam(value = "date_of_travel", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfTravel
    ) {
        Ticket ticket = new Ticket();
        if (passportno != null && !passportno.isEmpty()) {
            ticket.setPassportno(passportno);
        }
        if (dateOfTravel != null) {
            ticket.setDateOfTravel(dateOfTravel);
        }
        model.addAttribute("ticket", ticket);
        model.addAttribute("sources", flightService.findAllSources());
        String selected_origin = null;
        String selected_destination = null;
        List<DropDownMenuOptionDTO> destinations = null;
        List<DropDownMenuOptionDTO> flights = null;

        if (origin != null) {
            destinations = flightService.findAllDestinationsBySource(origin);
            selected_origin = origin;
        }

        if (origin != null && destination != null) {
            flights = flightService.findAllFlightsBySourceAndDestination(origin, destination);
            selected_destination = destination;
        }

        model.addAttribute("destinations", destinations);
        model.addAttribute("selected_origin", selected_origin);
        model.addAttribute("flights", flights);
        model.addAttribute("selected_destination", selected_destination);

        return "index";
    }

    @PostMapping("/ticket_sale")
    public String buyTicket(
            Model model,
            @ModelAttribute Ticket ticket,
            BindingResult binding
    ) {
        if (binding.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("\n");
            for (ObjectError error : binding.getAllErrors()) {
                errorMessage.append("\n").append(error.getDefaultMessage());
            }
            model.addAttribute("message", "Binding Error: " + errorMessage);
            model.addAttribute("theme", "error");
            return "index";
        }

        Object result = ticketService.saveTicket(ticket);
        List<String> stringList = new ArrayList<>();
        if (result instanceof List<?>) {
            List<?> tempList = (List<?>) result;
            boolean allStrings = tempList.stream().allMatch(objectString -> objectString instanceof String);
            if (allStrings) {
                stringList = tempList.stream()
                        .map(objectString -> (String) objectString)
                        .collect(Collectors.toList());
            }
        }

        if (stringList.size() == 1 && stringList.contains(TicketService.PASSENGER_DOES_NOT_EXIST)) {
            return "redirect:/passenger_registration?passportno=" + ticket.getPassportno();
        } else if (!stringList.isEmpty()) {
            model.addAttribute("message", stringList);
            model.addAttribute("theme", "error");
        } else {
            model.addAttribute("message", result.toString());
            model.addAttribute("theme", "success");
        }
        return "index";
    }

    @GetMapping("/passenger_registration")
    public String passengerRegistration(
            Model model,
            @RequestParam(value = "passportno", required = true) String passportno) {
        Passenger passenger = new Passenger();
        if (passportno != null && !passportno.isEmpty()) {
            passenger.setPassportno(passportno);
        }
        model.addAttribute("passenger", passenger);

        return "passenger_registration";
    }

    @PostMapping("/passenger_registration")
    public String registerPassenger(
            Model model,
            @ModelAttribute Passenger passenger,
            BindingResult binding
    ){
        return "passenger_registration";
    }



}
