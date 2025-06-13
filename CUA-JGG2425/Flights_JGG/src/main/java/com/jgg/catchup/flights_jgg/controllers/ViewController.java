package com.jgg.catchup.flights_jgg.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired
    private ObjectMapper objectMapper;

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
                String ticketJSON;
                try {
                    ticketJSON = objectMapper.writeValueAsString(ticket);
                } catch (JsonProcessingException exception) {
                    String errorMessage = String.join("Error while handling ticket: ", exception.getMessage());
                    redirectAttributes.addFlashAttribute("message", errorMessage);
                    return redirectTicketAttrib(redirectAttributes, passportno, dateOfTravel, origin, destination, flightCode);
                }
                redirectAttributes.addFlashAttribute("ticket", ticketJSON);
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
            @RequestParam(value = "passportno", required = false) String passportno,
            @RequestParam(value = "firstname", required = false) String firstname,
            @RequestParam(value = "lastname", required = false) String lastname,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "phone", required = false) String phone
            ) {
        Passenger passenger = new Passenger();
        if (passportno != null && !passportno.isEmpty()) {
            passenger.setPassportno(passportno);
        }
        if (firstname != null && !firstname.isEmpty()) {
            passenger.setFirstname(firstname);
        }
        if (lastname != null && !lastname.isEmpty()) {
            passenger.setLastname(lastname);
        }
        if (address != null && !address.isEmpty()) {
            passenger.setAddress(address);
        }
        if (phone != null && !phone.isEmpty()) {
            passenger.setPhone(phone);
        }
        if (!model.containsAttribute("ticket")) {
            model.addAttribute("ticket", "{}");
        }
        model.addAttribute("passenger", passenger);

        return "passenger_registration";
    }

    @PostMapping("/passenger_registration")
    public String registerPassenger(
            @ModelAttribute Passenger passenger,
            @RequestParam("ticketJson") String ticketJson,
            BindingResult binding,
            RedirectAttributes redirectAttributes
            ){
        if (binding.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : binding.getAllErrors()) {
                if (!errorMessage.isEmpty()) {
                    errorMessage.append("\n");
                }
                errorMessage.append(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("ticket", ticketJson );
            redirectAttributes.addFlashAttribute("message", "Validation error:\n" + errorMessage);
            redirectAttributes.addFlashAttribute("theme", "error");
            return "redirect:/passenger_registration?passportno=" + passenger.getPassportno();
        }
        if (passenger.getAddress() == null || passenger.getAddress().isEmpty()) {
            passenger.setAddress(null);
        }
        if (passenger.getPhone() == null || passenger.getPhone().isEmpty()) {
            passenger.setPhone(null);
        }
        passengerService.savePassenger(passenger);
        Ticket ticket;
        try {
            ticket = objectMapper.readValue(ticketJson, Ticket.class);
        } catch (JsonProcessingException exception) {
            redirectAttributes.addFlashAttribute("ticket", ticketJson );
            redirectAttributes.addFlashAttribute("message", "Error while parsing ticket: " + exception.getMessage());
            redirectAttributes.addFlashAttribute("theme", "error");
            return "redirect:/passenger_registration?passportno=" + passenger.getPassportno();
        }
        if (ticket != null) {
            Object result = ticketService.saveTicket(ticket);
            redirectAttributes.addFlashAttribute("message", result.toString());
            redirectAttributes.addFlashAttribute("theme", "success");
            return "redirect:/ticket_sale";
        } else {
            redirectAttributes.addFlashAttribute("ticket", ticketJson );
            redirectAttributes.addFlashAttribute("message", "Ticket hasn't been parsed correctly.");
            redirectAttributes.addFlashAttribute("theme", "error");
            return "redirect:/passenger_registration?passportno=" + passenger.getPassportno();
        }
    }

    @GetMapping("/ticket_cancelation")
    public String ticketCancelation(
            @RequestParam(value = "passportno", required = false) String passportno,
            @RequestParam(value = "flight_date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flight_date,
            Model model
    ) {
        if (passportno != null && !passportno.isEmpty()) {
            model.addAttribute("passportno", passportno);
        }
        if (flight_date != null) {
            model.addAttribute("flight_date", flight_date.format(DateTimeFormatter.ISO_DATE));
        }
        return "ticket_cancelation";
    }

    @PostMapping("/ticket_cancelation")
    public String cancelTicket(
            @RequestParam(value = "passportno") String passportno,
            @RequestParam(value = "flight_date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flight_date,
            BindingResult binding,
            RedirectAttributes redirectAttributes
    ) {
        if (binding.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : binding.getAllErrors()) {
                if (!errorMessage.isEmpty()) {
                    errorMessage.append("\n");
                }
                errorMessage.append(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("passportno", passportno );
            redirectAttributes.addFlashAttribute("flight_date", flight_date);
            redirectAttributes.addFlashAttribute("message", "Error while parsing ticket: " + errorMessage);
            redirectAttributes.addFlashAttribute("theme", "error");
            return "redirect:/ticket_cancelation" ;
        }

        return "redirect:/ticket_cancelation";
    }



}
