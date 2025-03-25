package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.IPassengerDAO;
import com.jgg.catchup.flights_jgg.models.entities.Passenger;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    @Autowired
    private IPassengerDAO passengerDAO;

    public ResponseEntity<Passenger> findPassengerById(@NotNull @PathVariable(value = "id") String id) {
        Optional<Passenger> passenger = passengerDAO.findById(id);
        return passenger.isPresent() ? ResponseEntity.ok(passenger.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> savePassenger(Passenger passenger) {
        List<String> errors = new ArrayList<>();

        if (passenger.getPassportno() == null) {
            errors.add("Associated Passenger's passportno not found");
        }
        if (passenger.getFirstname() == null) {
            errors.add("Associated Passenger's firstname not found");
        }
        if (passenger.getLastname() == null) {
            errors.add("Associated Passenger's lastname not found");
        }
        if (passengerDAO.existsById(passenger.getPassportno())) {
            errors.add("Passenger already exists.");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
        }

        passengerDAO.save(passenger);
        return ResponseEntity.status(HttpStatus.CREATED).body("Passenger saved successfully.");
    }
}
