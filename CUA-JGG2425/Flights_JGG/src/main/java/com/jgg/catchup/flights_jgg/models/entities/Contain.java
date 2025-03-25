package com.jgg.catchup.flights_jgg.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "contains")
public class Contain {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contains_id_gen")
    @SequenceGenerator(name = "contains_id_gen", sequenceName = "contains_code_seq", allocationSize = 1)
    @Column(name = "code", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airlineid", nullable = false)
    @JsonIgnoreProperties("contains")
    private Airline airlineid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airportid", nullable = false)
    @JsonIgnoreProperties("contains")
    private Airport airportid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Airline getAirlineid() {
        return airlineid;
    }

    public void setAirlineid(Airline airlineid) {
        this.airlineid = airlineid;
    }

    public Airport getAirportid() {
        return airportid;
    }

    public void setAirportid(Airport airportid) {
        this.airportid = airportid;
    }

}