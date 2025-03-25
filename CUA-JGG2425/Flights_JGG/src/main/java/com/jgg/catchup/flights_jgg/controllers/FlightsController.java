package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.entities.*;
import com.jgg.catchup.flights_jgg.services.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Flights")
public class FlightsController {

    @Autowired
    private FlightsService flightsService;

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> findFlightById(@PathVariable(value = "id") String id) {
        return flightsService.findFlightById(id);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> findAllDistinctOrigins() {
        return flightsService.findAllDistinctOrigins();
    }
}
