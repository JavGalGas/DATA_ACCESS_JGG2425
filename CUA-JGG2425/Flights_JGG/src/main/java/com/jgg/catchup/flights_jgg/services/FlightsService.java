package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.*;
import com.jgg.catchup.flights_jgg.models.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightsService {

    @Autowired
    private IFlightDAO flightDAO;

    public ResponseEntity<Flight> findFlightById(String id) {
        Optional<Flight> flight = flightDAO.findById(id);
        return flight.isPresent() ? ResponseEntity.ok(flight.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Flight>> findAllDistinctOrigins() {
        Optional<List<Flight>> origins = flightDAO.findAllOrigins();
        return origins.isPresent() ? ResponseEntity.ok(origins.get()) : ResponseEntity.notFound().build();
    }
}
