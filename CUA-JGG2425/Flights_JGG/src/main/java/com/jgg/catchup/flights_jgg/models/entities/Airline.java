package com.jgg.catchup.flights_jgg.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "airlines")
public class Airline {
    @Id
    @Column(name = "airlineid", nullable = false, length = 3)
    private String airlineid;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "shortcode", length = 3)
    private String shortcode;

    @OneToMany(mappedBy = "airlineid")
    @JsonIgnoreProperties("airlineid")
    private Set<com.jgg.catchup.flights_jgg.models.entities.Contain> contains = new LinkedHashSet<>();

    @OneToMany(mappedBy = "airlineid")
    @JsonIgnoreProperties("airlineid")
    private Set<com.jgg.catchup.flights_jgg.models.entities.Flight> flights = new LinkedHashSet<>();

    public String getAirlineid() {
        return airlineid;
    }

    public void setAirlineid(String airlineid) {
        this.airlineid = airlineid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public Set<com.jgg.catchup.flights_jgg.models.entities.Contain> getContains() {
        return contains;
    }

    public void setContains(Set<com.jgg.catchup.flights_jgg.models.entities.Contain> contains) {
        this.contains = contains;
    }

    public Set<com.jgg.catchup.flights_jgg.models.entities.Flight> getFlights() {
        return flights;
    }

    public void setFlights(Set<com.jgg.catchup.flights_jgg.models.entities.Flight> flights) {
        this.flights = flights;
    }

}