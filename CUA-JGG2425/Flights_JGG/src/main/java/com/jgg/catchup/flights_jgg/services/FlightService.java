package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.*;
import com.jgg.catchup.flights_jgg.models.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private IFlightDAO flightDAO;

    public ResponseEntity<Flight> findFlightById(String id) {
        Optional<Flight> flight = flightDAO.findById(id);
        return flight.isPresent() ? ResponseEntity.ok(flight.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<String>> findAllSources() {
        Optional<List<String>> origins = flightDAO.findAllSources();
        return origins.isPresent() ? ResponseEntity.ok(origins.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<String>> findAllDestinationsBySource(@Param("source") String source) {
        Optional<List<String>> destinations = flightDAO.findAllDestinationsBySource(source);
        return destinations.isPresent() ? ResponseEntity.ok(destinations.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Flight>> findAllFlightsBySourceAndDestination(@Param("source") String source, @Param("destination") String destination) {
        Optional<List<Flight>> flights = flightDAO.findAllFlightsBySourceAndDestination(source, destination);
        return flights.isPresent() ? ResponseEntity.ok(flights.get()) : ResponseEntity.notFound().build();
    }
}
