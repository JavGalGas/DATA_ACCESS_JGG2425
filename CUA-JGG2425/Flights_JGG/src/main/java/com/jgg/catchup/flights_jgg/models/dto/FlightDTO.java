package com.jgg.catchup.flights_jgg.models.dto;

public class FlightDTO {
    private String flight_code;
    private String source;
    private String destination;
    private String arrival;
    private String departure;
    private String status;
    private String duration;
    private String flightType;
    private Integer layoverTime;
    private Integer noOfStops;


    public String getFlight_code() {
        return flight_code;
    }

    public void setFlight_code(String flight_code) {
        this.flight_code = flight_code;
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
}
