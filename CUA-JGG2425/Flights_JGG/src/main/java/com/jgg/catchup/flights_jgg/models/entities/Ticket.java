package com.jgg.catchup.flights_jgg.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@DynamicInsert
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tickets_id_gen")
    @SequenceGenerator(name = "tickets_id_gen", sequenceName = "ticket1_ticket_number_seq", allocationSize = 1)
    @Column(name = "ticket_number", nullable = false)
    private Integer id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_of_booking", nullable = false)
    private LocalDate dateOfBooking;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_of_travel", nullable = false)
    private LocalDate dateOfTravel;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_of_cancellation")
    private LocalDate dateOfCancellation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "passportno", nullable = false)
    @JsonIgnoreProperties("tickets")
    private Passenger passportno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_code", nullable = false)
    @JsonIgnoreProperties("tickets")
    private Flight flightCode;

    @Column(name = "price")
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(LocalDate dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public LocalDate getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(LocalDate dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public LocalDate getDateOfCancellation() {
        return dateOfCancellation;
    }

    public void setDateOfCancellation(LocalDate dateOfCancellation) {
        this.dateOfCancellation = dateOfCancellation;
    }

    public Passenger getPassportno() {
        return passportno;
    }

    public void setPassportno(Passenger passportno) {
        this.passportno = passportno;
    }

    public Flight getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(Flight flightCode) {
        this.flightCode = flightCode;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}