package com.jgg.catchup.flights_jgg.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @Size(max = 10)
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{6,9}$\n", message = "Passport number must be in a valid format")
    @Column(name = "passportno", nullable = false, length = 10)
    private String passportno;

    @Size(max = 20)
    @NotBlank
    @Column(name = "firstname", nullable = false, length = 20)
    private String firstname;

    @Size(max = 20)
    @NotBlank
    @Column(name = "lastname", nullable = false, length = 20)
    private String lastname;

    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;

    @Size(min = 7, max = 12)
    @Pattern(regexp = "\\d{7,12}", message = "Phone must be between 7 and 12 digits")
    @Column(name = "phone", length = 12)
    private String phone;

    @Size(max = 1)
    @Pattern(regexp = "[MF]", message = "Sex must be 'M' or 'F'")
    @Column(name = "sex", length = 1)
    private String sex;

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

}