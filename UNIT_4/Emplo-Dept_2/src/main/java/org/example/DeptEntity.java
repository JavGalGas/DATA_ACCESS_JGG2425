package org.example;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "dept", schema = "public", catalog = "EmployeeDB")
public class DeptEntity {
//    @OneToMany(mappedBy = "Department")
//    private List<EmployeeEntity> employees;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "deptno")
    private int deptno;
    @Basic
    @Column(name = "dname")
    private String dname;
    @Basic
    @Column(name = "loc")
    private String loc;
    @OneToMany(mappedBy = "department")
    private Set<EmployeeEntity> employeeList;

//    public List<EmployeeEntity> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(List<EmployeeEntity> employees) {
//        this.employees = employees;
//    }

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptEntity that = (DeptEntity) o;
        return deptno == that.deptno && Objects.equals(dname, that.dname) && Objects.equals(loc, that.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptno, dname, loc);
    }

    public Set<EmployeeEntity> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(Set<EmployeeEntity> employeList) {
        this.employeeList = employeList;
    }
}
