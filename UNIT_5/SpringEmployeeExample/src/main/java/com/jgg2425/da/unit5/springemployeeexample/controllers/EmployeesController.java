package com.jgg2425.da.unit5.springemployeeexample.controllers;

import com.jgg2425.da.unit5.springemployeeexample.models.dao.IEmployeeEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.EmployeeEntity;
import com.jgg2425.da.unit5.springemployeeexample.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @GetMapping
    public List<EmployeeEntity> findAllEmployees(){
        return employeesService.findAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> findEmployeeById(@PathVariable(value = "id") int id) {
        return employeesService.findEmployeeById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeEntity employee,@PathVariable(value = "id") int id) {
        return employeesService.updateEmployee(employee,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int id) {
        return employeesService.deleteEmployee(id);
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@Validated @RequestBody EmployeeEntity employee) {
        return employeesService.saveEmployee(employee);
    }
}
