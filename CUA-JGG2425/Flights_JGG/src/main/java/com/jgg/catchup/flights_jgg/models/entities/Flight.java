package com.jgg.catchup.flights_jgg.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @Column(name = "flight_code", nullable = false, length = 10)
    private String flightCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source", nullable = false)
    @JsonIgnoreProperties("departing_flights")
    private Airport source;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destination", nullable = false)
    @JsonIgnoreProperties("incoming_flights")
    private Airport destination;

    @Column(name = "arrival", nullable = false, length = 10)
    private String arrival;

    @Column(name = "departure", nullable = false, length = 10)
    private String departure;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "flight_type", length = 10)
    private String flightType;

    @Column(name = "layover_time")
    private Integer layoverTime;

    @Column(name = "no_of_stops")
    private Integer noOfStops;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airlineid", nullable = false)
    @JsonIgnoreProperties("flights")
    private Airline airlineid;

    @Column(name = "seats")
    private Integer seats;

    @OneToMany(mappedBy = "flightCode")
    @JsonIgnoreProperties("flightCode")
    private Set<com.jgg.catchup.flights_jgg.models.entities.Ticket> tickets = new LinkedHashSet<>();

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Airport getSource() {
        return source;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public Integer getLayoverTime() {
        return layoverTime;
    }

    public void setLayoverTime(Integer layoverTime) {
        this.layoverTime = layoverTime;
    }

    public Integer getNoOfStops() {
        return noOfStops;
    }

    public void setNoOfStops(Integer noOfStops) {
        this.noOfStops = noOfStops;
    }

    public Airline getAirlineid() {
        return airlineid;
    }

    public void setAirlineid(Airline airlineid) {
        this.airlineid = airlineid;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Set<com.jgg.catchup.flights_jgg.models.entities.Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<com.jgg.catchup.flights_jgg.models.entities.Ticket> tickets) {
        this.tickets = tickets;
    }

}