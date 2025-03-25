package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.*;
import com.jgg.catchup.flights_jgg.models.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlightsService {

    @Autowired
    private IAirlineDAO airlineDAO;
    @Autowired
    private IAirportDAO airportDAO;
    @Autowired
    private ICityDAO cityDAO;
    @Autowired
    private IContainDAO containDAO;
    @Autowired
    private IFlightDAO flightDAO;

    public ResponseEntity<Airline> findAirlineById(String id) {
        Optional<Airline> airline = airlineDAO.findById(id);
        return airline.isPresent() ? ResponseEntity.ok(airline.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<Airport> findAirportById(String id) {
        Optional<Airport> airport = airportDAO.findById(id);
        return airport.isPresent() ? ResponseEntity.ok(airport.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<City> findCityById(String id) {
        Optional<City> city = cityDAO.findById(id);
        return city.isPresent() ? ResponseEntity.ok(city.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<Contain> findContainById(int id) {
        Optional<Contain> contain = containDAO.findById(id);
        return contain.isPresent() ? ResponseEntity.ok(contain.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<Flight> findFlightById(String id) {
        Optional<Flight> flight = flightDAO.findById(id);
        return flight.isPresent() ? ResponseEntity.ok(flight.get()) : ResponseEntity.notFound().build();
    }
}
