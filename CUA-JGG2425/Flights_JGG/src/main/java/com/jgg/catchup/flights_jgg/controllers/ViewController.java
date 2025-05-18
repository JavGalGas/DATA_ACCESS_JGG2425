package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import com.jgg.catchup.flights_jgg.models.entities.Flight;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            @RequestParam(value = "selected_flight", required = false) String flightCode,
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
        String selected_flight = null;
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

        if (origin != null && destination != null && flightCode != null) {
            selected_flight = flightCode;
        }

        model.addAttribute("destinations", destinations);
        model.addAttribute("selected_origin", selected_origin);
        model.addAttribute("flights", flights);
        model.addAttribute("selected_destination", selected_destination);
        model.addAttribute("selected_flight", selected_flight);

        return "index";
    }

    @PostMapping("/ticket_sale")
    public String buyTicket(
            @ModelAttribute Ticket ticket,
            BindingResult binding,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "origin_code", required = false) String origin,
            @RequestParam(value = "destination_code", required = false) String destination
    ) {
        String passportno = ticket.getPassportno();
        LocalDate dateOfTravel = ticket.getDateOfTravel();
        String flightCode = ticket.getFlightCode();

        if (binding.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : binding.getAllErrors()) {
                if (!errorMessage.isEmpty()) {
                    errorMessage.append("\n");
                }
                errorMessage.append(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("message", "Error de validación:\n" + errorMessage.toString());
            return redirectTicketAttrib(redirectAttributes, passportno, dateOfTravel, origin, destination, flightCode);
        }

        ticket.setDateOfBooking(LocalDate.now());

        Object result = ticketService.saveTicket(ticket);

        if (result instanceof List<?>) {
            List<?> tempList = (List<?>) result;
            List<String> stringList = new ArrayList<>();

            boolean allStrings = tempList.stream().allMatch(objectString -> objectString instanceof String);
            if (allStrings) {
                stringList = tempList.stream()
                        .map(objectString -> (String) objectString)
                        .collect(Collectors.toList());
            }

            if (stringList.size() == 1 && stringList.contains(TicketService.PASSENGER_DOES_NOT_EXIST)) {
                return "redirect:/passenger_registration?passportno=" + passportno;
            } else if (!stringList.isEmpty()) {
                String errorMessage = String.join("\n", stringList);
                redirectAttributes.addFlashAttribute("message", errorMessage);
                return redirectTicketAttrib(redirectAttributes, passportno, dateOfTravel, origin, destination, flightCode);
            }
        }

        redirectAttributes.addFlashAttribute("message", result.toString());
        redirectAttributes.addFlashAttribute("theme", "success");

        return "redirect:/ticket_sale";
    }

    private String redirectTicketAttrib(RedirectAttributes redirectAttributes, String passportno,
                                        LocalDate dateOfTravel, String origin, String destination,
                                        String flight) {
        redirectAttributes.addFlashAttribute("theme", "error");

        if (passportno != null && !passportno.isEmpty()) {
            redirectAttributes.addAttribute("passportno", passportno);
        }

        if (dateOfTravel != null) {
            redirectAttributes.addAttribute("date_of_travel", dateOfTravel.format(DateTimeFormatter.ISO_DATE));
        }

        if (origin != null && !origin.isEmpty()) {
            redirectAttributes.addAttribute("selected_origin", origin);
        }

        if (destination != null && !destination.isEmpty()) {
            redirectAttributes.addAttribute("selected_destination", destination);
        }

        if (flight != null && !flight.isEmpty()) {
            redirectAttributes.addAttribute("selected_flight", flight);
        }

        return "redirect:/ticket_sale";
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
