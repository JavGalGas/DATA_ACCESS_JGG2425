package com.jgg.catchup.flights_jgg.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @Size(max = 3)
    @NotBlank
    @Column(name = "code", nullable = false, length = 3)
    private String code;

    @Size(max = 15)
    @NotBlank
    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Size(max = 15)
    @NotBlank
    @Column(name = "state", nullable = false, length = 15)
    private String state;

    @Size(max = 30)
    @NotBlank
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