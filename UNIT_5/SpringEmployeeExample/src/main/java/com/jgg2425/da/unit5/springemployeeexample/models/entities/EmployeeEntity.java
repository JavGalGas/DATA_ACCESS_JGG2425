package com.jgg2425.da.unit5.springemployeeexample.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @Column(name = "empno", nullable = false)
    private Integer id;

    @Column(name = "ename", length = 10)
    private String ename;

    @Column(name = "job", length = 9)
    private String job;

    @ManyToOne
    @JoinColumn(name = "deptno")
    @JsonIgnoreProperties("employees")
    private DeptEntity deptno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public DeptEntity getDeptno() {
        return deptno;
    }

    public void setDeptno(DeptEntity deptno) {
        this.deptno = deptno;
    }

}