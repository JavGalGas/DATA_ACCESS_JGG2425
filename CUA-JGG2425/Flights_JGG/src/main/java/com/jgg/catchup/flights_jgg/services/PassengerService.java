package com.jgg.catchup.flights_jgg.services;

import com.jgg.catchup.flights_jgg.models.dao.IPassengerDAO;
import com.jgg.catchup.flights_jgg.models.entities.Passenger;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    @Autowired
    private IPassengerDAO passengerDAO;

    public Passenger findPassengerByPassportNo(@NotBlank @PathVariable(value = "id") String passportNo) {
        Optional<Passenger> passenger = passengerDAO.findById(passportNo);
        return passenger.isPresent() ? passenger.get() : null;
    }

    public Object savePassenger(@Validated Passenger passenger) {
        List<String> errors = new ArrayList<>();

        if (passenger.getPassportno() == null) {
            errors.add("Passenger passport number is required.");
        }
        if (passenger.getFirstname() == null) {
            errors.add("Passenger firstname is required.");
        }
        if (passenger.getLastname() == null) {
            errors.add("Passenger lastname is required.");
        }
        if (passengerDAO.existsById(passenger.getPassportno())) {
            errors.add("Passenger already exists.");
        }

        if (!errors.isEmpty()) {
            return errors;
        }
        passengerDAO.save(passenger);
        return "Passenger saved.";
    }

}
