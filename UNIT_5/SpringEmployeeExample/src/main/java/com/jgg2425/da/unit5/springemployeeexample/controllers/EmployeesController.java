package com.jgg2425.da.unit5.springemployeeexample.controllers;

import com.jgg2425.da.unit5.springemployeeexample.models.dao.IEmployeeEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/employees")
public class EmployeesController {

    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;

    @GetMapping
    public List<EmployeeEntity> findAllEmployees(){
        return (List<EmployeeEntity>) employeeEntityDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> findEmployeeById(@PathVariable(value = "id") int id) {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok().body(employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeEntity employee,@PathVariable(value = "id") int id) {
        Optional<EmployeeEntity> employeeEntity = employeeEntityDAO.findById(id);
        if (employeeEntity.isPresent()) {
            employeeEntity.get().setEname(employee.getEname());
            employeeEntity.get().setJob(employee.getJob());
            employeeEntity.get().setDeptno(employee.getDeptno());
            employeeEntityDAO.save(employee);
            return ResponseEntity.ok().body("Updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int id) {
        Optional<EmployeeEntity> employeeEntity = employeeEntityDAO.findById(id);
        if (employeeEntity.isPresent()) {
            employeeEntityDAO.delete(employeeEntity.get());
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public EmployeeEntity saveEmployee(@Validated @RequestBody EmployeeEntity employee) {
        return employeeEntityDAO.save(employee);
    }
}
