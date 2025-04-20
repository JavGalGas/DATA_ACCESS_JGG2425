package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.dto.DropDownMenuOptionDTO;
import com.jgg.catchup.flights_jgg.models.entities.*;
import com.jgg.catchup.flights_jgg.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/flights/{flight_code}")
    public ResponseEntity<Flight> findFlightByFlightCode(@PathVariable(value = "flight_code") String flightCode) {
        Flight result = flightService.findFlightByFlightCode(flightCode);
        return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }

    @GetMapping("/sources")
    public ResponseEntity<List<DropDownMenuOptionDTO>> findAllSources() {
        List<DropDownMenuOptionDTO> result = flightService.findAllSources();
        return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }

    @GetMapping("/destinations")
    public ResponseEntity<List<DropDownMenuOptionDTO>> findAllDestinationsBySource(
            @RequestParam(value = "source") String source
    ) {
        List<DropDownMenuOptionDTO> result = flightService.findAllDestinationsBySource(source);
        return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }

    @GetMapping("/flights")
    public ResponseEntity<List<DropDownMenuOptionDTO>> findAllFlightsBySourceAndDestination(
            @RequestParam(value = "source") String source,
            @RequestParam(value = "destination") String destination
    ) {
        List<DropDownMenuOptionDTO> result = flightService.findAllFlightsBySourceAndDestination(source, destination);
        return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }
}
