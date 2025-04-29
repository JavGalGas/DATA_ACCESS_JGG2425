package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import com.jgg.catchup.flights_jgg.models.entities.Ticket;
import com.jgg.catchup.flights_jgg.services.FlightService;
import com.jgg.catchup.flights_jgg.services.PassengerService;
import com.jgg.catchup.flights_jgg.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
            @RequestParam(value = "selected_destination", required = false) String destination
    ) {
        model.addAttribute("ticket", new Ticket());
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


        return "index";
    }
}
