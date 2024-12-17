package com.jgg2425.da.unit5.springemployeeexample.services;

import com.jgg2425.da.unit5.springemployeeexample.models.dao.IDeptEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.dao.IEmployeeEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.dto.EmployeeDTO;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.DeptEntity;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeesService {
    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;

    @Autowired
    private IDeptEntityDAO deptEntityDAO;

    public List<EmployeeEntity> findAllEmployees(){
        return (List<EmployeeEntity>) employeeEntityDAO.findAll();
    }

    public ResponseEntity<EmployeeEntity> findEmployeeById(int id) {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok().body(employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateEmployee(EmployeeEntity employee, int id) {
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

    public ResponseEntity<?> deleteEmployee(int id) {
        Optional<EmployeeEntity> employeeEntity = employeeEntityDAO.findById(id);
        if (employeeEntity.isPresent()) {
            employeeEntityDAO.delete(employeeEntity.get());
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> saveEmployee(EmployeeEntity employee) {
        if (employeeEntityDAO.existsById(employee.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee already exists.");
        } else {
            employeeEntityDAO.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee saved successfully.");
        }
    }


    public List<EmployeeDTO> findAllEmployeesDTO(){
        List<EmployeeEntity> employees = (List<EmployeeEntity>) employeeEntityDAO.findAll();
        List<EmployeeDTO> employeesDTO = new ArrayList<>();
        for (EmployeeEntity employee : employees) {
            Optional<DeptEntity> dept = deptEntityDAO.findById(employee.getDeptno().getId());

            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setEname(employee.getEname());
            employeeDTO.setJob(employee.getJob());
            employeeDTO.setDeptno(dept.get().getId());
            employeeDTO.setDeptName(dept.get().getDname());
            employeeDTO.setLocation(dept.get().getLoc());
            employeesDTO.add(employeeDTO);
        }
        return employeesDTO;
    }

    public ResponseEntity<EmployeeDTO> findEmployeeDTOById(int id) {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        if (employee.isPresent()) {
            Optional<DeptEntity> dept = deptEntityDAO.findById(employee.get().getDeptno().getId());
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.get().getId());
            employeeDTO.setEname(employee.get().getEname());
            employeeDTO.setJob(employee.get().getJob());
            employeeDTO.setDeptno(dept.get().getId());
            employeeDTO.setDeptName(dept.get().getDname());
            employeeDTO.setLocation(dept.get().getLoc());
            return ResponseEntity.ok().body(employeeDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
