package com.jgg2425.da.unit5.springemployeeexample.controllers;

import com.jgg2425.da.unit5.springemployeeexample.models.dao.IDeptEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.DeptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/departments")
public class DeptsController {

    @Autowired
    private IDeptEntityDAO deptEntityDAO;

    @GetMapping
    public List<DeptEntity> findAllDepts(){
        return (List<DeptEntity>) deptEntityDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeptEntity> findDeptById(@PathVariable(value = "id") int id) {
        Optional<DeptEntity> dept = deptEntityDAO.findById(id);
        if (dept.isPresent()) {
            return ResponseEntity.ok().body(dept.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDept(@RequestBody @Validated DeptEntity dept,
                                                 @PathVariable(value = "id") int id) {
        Optional<DeptEntity> deptEntity = deptEntityDAO.findById(id);
        if (deptEntity.isPresent()) {
            deptEntity.get().setDname(dept.getDname());
            deptEntity.get().setLoc(dept.getLoc());
            deptEntity.get().setEmployees(dept.getEmployees());
            deptEntityDAO.save(deptEntity.get());
            return ResponseEntity.ok().body("Updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDept(@PathVariable(value = "id") int id) {
        Optional<DeptEntity> deptEntity = deptEntityDAO.findById(id);
        if (deptEntity.isPresent()) {
            deptEntityDAO.delete(deptEntity.get());
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public DeptEntity saveDept(@Validated @RequestBody DeptEntity dept) {
        return deptEntityDAO.save(dept);
    }
}
