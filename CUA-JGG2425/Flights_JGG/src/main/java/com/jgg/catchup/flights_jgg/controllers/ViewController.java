package com.jgg.catchup.flights_jgg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

    @Autowired
    private FlightController flightController;
    @Autowired
    private PassengerController passengerController;
    @Autowired
    private TicketController ticketController;


}
