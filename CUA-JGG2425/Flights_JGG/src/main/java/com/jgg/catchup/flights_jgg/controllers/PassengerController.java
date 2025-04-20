package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.entities.Passenger;
import com.jgg.catchup.flights_jgg.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping("passengers/{passportno}")
    public ResponseEntity<Passenger> findPassengerByPassportNo(@PathVariable(value = "passportno") String passportno) {
        Passenger passenger = passengerService.findPassengerByPassportNo(passportno);
        return passenger == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(passenger);
    }

    @PostMapping("")
    public ResponseEntity<?> savePassenger(@RequestBody Passenger passenger) {
        Object result = passengerService.savePassenger(passenger);
        if (result instanceof List) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }
}
