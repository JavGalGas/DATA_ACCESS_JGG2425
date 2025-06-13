package com.jgg.catchup.flights_jgg_javafx.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

}