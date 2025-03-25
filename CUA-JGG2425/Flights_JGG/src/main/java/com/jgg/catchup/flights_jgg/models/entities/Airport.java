package com.jgg.catchup.flights_jgg.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @Column(name = "code", nullable = false, length = 4)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city", nullable = false)
    @JsonIgnoreProperties("airports")
    private com.jgg.catchup.flights_jgg.models.entities.City city;

    @OneToMany(mappedBy = "airportid")
    @JsonIgnoreProperties("airportid")
    private Set<com.jgg.catchup.flights_jgg.models.entities.Contain> contains = new LinkedHashSet<>();

    @OneToMany(mappedBy = "source")
    @JsonIgnoreProperties("source")
    private Set<com.jgg.catchup.flights_jgg.models.entities.Flight> departing_flights = new LinkedHashSet<>();

    @OneToMany(mappedBy = "destination")
    @JsonIgnoreProperties("destination")
    private Set<com.jgg.catchup.flights_jgg.models.entities.Flight> incoming_flights = new LinkedHashSet<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.jgg.catchup.flights_jgg.models.entities.City getCity() {
        return city;
    }

    public void setCity(com.jgg.catchup.flights_jgg.models.entities.City city) {
        this.city = city;
    }

    public Set<com.jgg.catchup.flights_jgg.models.entities.Contain> getContains() {
        return contains;
    }

    public void setContains(Set<com.jgg.catchup.flights_jgg.models.entities.Contain> contains) {
        this.contains = contains;
    }

    public Set<com.jgg.catchup.flights_jgg.models.entities.Flight> getDeparting_flights() {
        return departing_flights;
    }

    public void setDeparting_flights(Set<com.jgg.catchup.flights_jgg.models.entities.Flight> departing_flights) {
        this.departing_flights = departing_flights;
    }

    public Set<com.jgg.catchup.flights_jgg.models.entities.Flight> getIncoming_flights() {
        return incoming_flights;
    }

    public void setIncoming_flights(Set<com.jgg.catchup.flights_jgg.models.entities.Flight> incoming_flights) {
        this.incoming_flights = incoming_flights;
    }

}