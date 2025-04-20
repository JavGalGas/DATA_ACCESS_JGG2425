package com.jgg.catchup.flights_jgg.controllers;

import com.jgg.catchup.flights_jgg.models.entities.Ticket;
import com.jgg.catchup.flights_jgg.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> findTicketById(@PathVariable int id) {
        Ticket ticket = ticketService.findTicketById(id);
        return ticket == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(ticket);
    }

    @PostMapping("")
    public ResponseEntity<?> saveTicket(@RequestBody Ticket ticket) {
        Object result = ticketService.saveTicket(ticket);
        if (result instanceof List) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }
}
