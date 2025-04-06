package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.*;
import com.jgg.catchup.flights_jgg.models.dto.FlightDTO;
import com.jgg.catchup.flights_jgg.models.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ResponseEntity<List<FlightDTO>> findAllFlightsBySourceAndDestination(@Param("source") String source, @Param("destination") String destination) {
        Optional<List<Flight>> flights = flightDAO.findAllFlightsBySourceAndDestination(source, destination);

        if (flights.isPresent()) {
            ModelMapper mapper = new ModelMapper();
            List<FlightDTO> flightDTOS = flights.get().stream()
                    .map(flight -> mapper.map(flight, FlightDTO.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(flightDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
