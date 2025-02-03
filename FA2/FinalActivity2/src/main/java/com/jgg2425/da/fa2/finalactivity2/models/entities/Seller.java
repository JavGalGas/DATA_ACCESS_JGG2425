package com.jgg2425.da.fa2.finalactivity2.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.LinkedHashSet;
import java.util.Set;
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sellers_id_gen")
    @SequenceGenerator(name = "sellers_id_gen", sequenceName = "sellers_seller_id_seq", allocationSize = 1)
    @Column(name = "seller_id", nullable = false)
    private Integer id;

    @Column(name = "cif", nullable = false, length = 20)
    private String cif;

    @Size(max = 100, message = "Name size must be less than 100")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100, message = "Business name size must be less than 100")
    @Column(name = "business_name", length = 100)
    private String businessName;

    @Size(max = 15, message = "Phone size must be less than 15")
    @Column(name = "phone", length = 15)
    private String phone;

    @Email
    @Size(max = 90, message = "Email size must be less than 90")
    @Column(name = "email", length = 90)
    private String email;

    @Size(max = 50, message = "Plain password size must be less than 50")
    @Column(name = "plain_password", nullable = false, length = 50)
    private String plainPassword;

    @Size(max = 100, message = "Password size must be less than 100")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @OneToMany(mappedBy = "seller")
    @JsonIgnoreProperties("seller")
    private Set<SellerProduct> sellerProducts = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<SellerProduct> getSellerProducts() {
        return sellerProducts;
    }

    public void setSellerProducts(Set<SellerProduct> sellerProducts) {
        this.sellerProducts = sellerProducts;
    }

}