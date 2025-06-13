package com.jgg.catchup.flights_jgg_javafx.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @Column(name = "code", nullable = false, length = 4)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "city", nullable = false)
    private String city;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}