package com.jgg2425.da.unit5.springemployeeexample.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "dept")
public class DeptEntity {
    @Column(name = "dname", length = 14)
    private String dname;

    @Column(name = "loc", length = 13)
    private String loc;

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

}