package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "dept", schema = "public", catalog = "EmployeeDB")
public class DeptEntity {
    @OneToMany(mappedBy = "Department")
    private List<EmployeeEntity> Employees;

    public List<EmployeeEntity> getEmployees() {
        return Employees;
    }

    public void setEmployees(List<EmployeeEntity> employees) {
        Employees = employees;
    }
}
