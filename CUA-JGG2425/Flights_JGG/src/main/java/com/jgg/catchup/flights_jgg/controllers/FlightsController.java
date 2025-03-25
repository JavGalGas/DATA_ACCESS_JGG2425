package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.entities.*;
import com.jgg.catchup.flights_jgg.services.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Flights")
public class FlightsController {

    @Autowired
    private FlightsService flightsService;

    @GetMapping("/airlines/{id}")
    public ResponseEntity<Airline> findAirlineById(@PathVariable(value = "id") String id) {
        return flightsService.findAirlineById(id);
    }

    @GetMapping("/airports/{id}")
    public ResponseEntity<Airport> findAirportById(@PathVariable(value = "id") String id) {
        return flightsService.findAirportById(id);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<City> findCityById(@PathVariable(value = "id") String id) {
        return flightsService.findCityById(id);
    }

    @GetMapping("/contains/{id}")
    public ResponseEntity<Contain> findContainById(@PathVariable(value = "id") int id) {
        return flightsService.findContainById(id);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> findFlightById(@PathVariable(value = "id") String id) {
        return flightsService.findFlightById(id);
    }
}
