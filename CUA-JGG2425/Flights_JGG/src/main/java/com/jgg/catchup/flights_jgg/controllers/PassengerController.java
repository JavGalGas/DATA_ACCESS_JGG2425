package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.entities.Passenger;
import com.jgg.catchup.flights_jgg.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> findPassengerById(@PathVariable(value = "id") String id) {
        return passengerService.findPassengerById(id);
    }

    @PostMapping("")
    public ResponseEntity<?> savePassenger(@RequestBody @Validated Passenger passenger) {
        return passengerService.savePassenger(passenger);
    }
}
