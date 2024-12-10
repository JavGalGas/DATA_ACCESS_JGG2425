package com.jgg2425.da.unit5.springemployeeexample.services;

import com.jgg2425.da.unit5.springemployeeexample.models.dao.IEmployeeEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.DeptEntity;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeesService {
    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;

    public List<EmployeeEntity> findAllEmployees(){
        return (List<EmployeeEntity>) employeeEntityDAO.findAll();
    }

    public ResponseEntity<EmployeeEntity> findEmployeeById(@PathVariable(value = "id") int id) {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok().body(employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeEntity employee, @PathVariable(value = "id") int id) {
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

    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int id) {
        Optional<EmployeeEntity> employeeEntity = employeeEntityDAO.findById(id);
        if (employeeEntity.isPresent()) {
            employeeEntityDAO.delete(employeeEntity.get());
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> saveEmployee(@Validated @RequestBody EmployeeEntity employee) {
        if (employeeEntityDAO.existsById(employee.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee already exists.");
        } else {
            employeeEntityDAO.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee saved successfully.");
        }
    }


    public List<EmployeeDTO> findAllEmployeesDTO(){
        List<EmployeeEntity> employees = (List<EmployeeEntity>) employeeEntityDAO.findAll();
        DeptsService deptsService = new DeptsService();
        List<EmployeeDTO> employeesDTO = new ArrayList<>();
        for (EmployeeEntity employee : employees) {
            DeptEntity dept = deptsService.findDeptById(1).getBody();
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setEname(employee.getEname());
            employeeDTO.setJob(employee.getJob());
            employeeDTO.setDeptName(dept.getDname());
            employeeDTO.setLocation(dept.getLoc());
            employeesDTO.add(employeeDTO);
        }
    }

    public ResponseEntity<EmployeeEntity> findEmployeeDTOById(@PathVariable(value = "id") int id) {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok().body(employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateEmployeeDTO(@RequestBody EmployeeEntity employee, @PathVariable(value = "id") int id) {
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

    public ResponseEntity<?> deleteEmployeeDTO(@PathVariable(value = "id") int id) {
        Optional<EmployeeEntity> employeeEntity = employeeEntityDAO.findById(id);
        if (employeeEntity.isPresent()) {
            employeeEntityDAO.delete(employeeEntity.get());
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> saveEmployeeDTO(@Validated @RequestBody EmployeeEntity employee) {
        if (employeeEntityDAO.existsById(employee.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee already exists.");
        } else {
            employeeEntityDAO.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee saved successfully.");
        }
    }
}
