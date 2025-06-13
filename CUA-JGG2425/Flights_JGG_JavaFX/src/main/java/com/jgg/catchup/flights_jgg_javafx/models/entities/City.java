package com.jgg.catchup.flights_jgg_javafx.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @Column(name = "code", nullable = false, length = 3)
    private String code;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Column(name = "state", nullable = false, length = 15)
    private String state;

    @Column(name = "country", nullable = false, length = 30)
    private String country;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}