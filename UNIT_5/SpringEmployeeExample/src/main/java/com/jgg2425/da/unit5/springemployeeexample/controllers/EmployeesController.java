package com.jgg2425.da.unit5.springemployeeexample.controllers;

import com.jgg2425.da.unit5.springemployeeexample.models.entities.EmployeeEntity;
import com.jgg2425.da.unit5.springemployeeexample.models.dto.EmployeeDTO;
import com.jgg2425.da.unit5.springemployeeexample.services.EmployeesService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @GetMapping
    public List<EmployeeEntity> findAllEmployees(){
        return employeesService.findAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> findEmployeeById(@NotNull @Min(0) @PathVariable(value = "id") int id) {
        return employeesService.findEmployeeById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@NotNull @RequestBody EmployeeEntity employee,
                                            @NotNull @Min(0) @PathVariable(value = "id") int id) {
        return employeesService.updateEmployee(employee,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@NotNull @Min(0) @PathVariable(value = "id") int id) {
        return employeesService.deleteEmployee(id);
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@NotNull @Validated @RequestBody EmployeeEntity employee) {
        return employeesService.saveEmployee(employee);
    }

    @GetMapping("/dto")
    public List<EmployeeDTO> findAllEmployeesDTO(){
        return employeesService.findAllEmployeesDTO();
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<EmployeeDTO> findEmployeeDTOById(@NotNull @Min(0) @PathVariable(value = "id") int id) {
        return employeesService.findEmployeeDTOById(id);
    }

}
