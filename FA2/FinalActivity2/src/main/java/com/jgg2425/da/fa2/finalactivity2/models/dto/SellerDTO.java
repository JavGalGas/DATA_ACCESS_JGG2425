package com.jgg2425.da.fa2.finalactivity2.models.dto;

import java.util.Optional;

public class SellerDTO {
    private Optional<String> cif;
    private String name;
    private String businessName;
    private String phone;
    private String email;
    private String plainPassword;

    public String getCif() {
        return cif.orElse("");
    }
    public void setCif(String cif) {
        this.cif = Optional.ofNullable(cif);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }
}
