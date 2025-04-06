package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.dto.FlightDTO;
import com.jgg.catchup.flights_jgg.models.entities.*;
import com.jgg.catchup.flights_jgg.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> findFlightById(@PathVariable(value = "id") String id) {
        return flightService.findFlightById(id);
    }

    @GetMapping("/sources")
    public ResponseEntity<List<String>> findAllSources() {
        return flightService.findAllSources();
    }

    @GetMapping("/destinations")
    public ResponseEntity<List<String>> findAllDestinationsBySource(
            @RequestParam(value = "source") String source
    ) {
        return flightService.findAllDestinationsBySource(source);
    }

    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> findAllFlightsBySourceAndDestination(
            @RequestParam(value = "source") String source,
            @RequestParam(value = "destination") String destination
    ) {
        return flightService.findAllFlightsBySourceAndDestination(source, destination);
    }
}
