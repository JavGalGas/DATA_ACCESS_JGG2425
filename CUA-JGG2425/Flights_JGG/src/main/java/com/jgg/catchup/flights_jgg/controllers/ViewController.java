package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.entities.Ticket;
import com.jgg.catchup.flights_jgg.services.FlightService;
import com.jgg.catchup.flights_jgg.services.PassengerService;
import com.jgg.catchup.flights_jgg.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        if (origin != null) {
            model.addAttribute("destinations", flightService.findAllDestinationsBySource(origin));
            model.addAttribute("selected_origin", origin);
        }

        if (origin != null && destination != null) {
            model.addAttribute("flights", flightService.findAllFlightsBySourceAndDestination(origin, destination));
            model.addAttribute("selected_destination", destination);
        }

        return "index";
    }

    @PostMapping("/ticket_sale")
    public String buyTicket(

    ) {
        return "index";
    }
}
