package com.jgg.catchup.flights_jgg_javafx.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @Column(name = "flight_code", nullable = false, length = 10)
    private String flightCode;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "destination", nullable = false)
    private String destination;

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

    @Column(name = "airlineid", nullable = false)
    private String airlineid;

    @Column(name = "seats")
    private Integer seats;

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
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

    public String getAirlineid() {
        return airlineid;
    }

    public void setAirlineid(String airlineid) {
        this.airlineid = airlineid;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

}