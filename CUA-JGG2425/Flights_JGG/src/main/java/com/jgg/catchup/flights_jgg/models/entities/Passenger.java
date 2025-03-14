package com.jgg.catchup.flights_jgg.models.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @SequenceGenerator(name = "passengers_id_gen", sequenceName = "contains_code_seq", allocationSize = 1)
    @Column(name = "passportno", nullable = false, length = 10)
    private String passportno;

    @Column(name = "firstname", nullable = false, length = 20)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 20)
    private String lastname;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "phone", length = 12)
    private String phone;

    @Column(name = "sex", length = 1)
    private String sex;

    @OneToMany(mappedBy = "passportno")
    private Set<com.jgg.catchup.flights_jgg.models.entities.Ticket> tickets = new LinkedHashSet<>();

    public String getPassportno() {
        return passportno;
    }

    public void setPassportno(String passportno) {
        this.passportno = passportno;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Set<com.jgg.catchup.flights_jgg.models.entities.Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<com.jgg.catchup.flights_jgg.models.entities.Ticket> tickets) {
        this.tickets = tickets;
    }

}