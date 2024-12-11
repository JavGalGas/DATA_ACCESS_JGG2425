package com.jgg2425.da.unit5.springemployeeexample.controllers;

import com.jgg2425.da.unit5.springemployeeexample.models.dto.DeptDTO;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.DeptEntity;
import com.jgg2425.da.unit5.springemployeeexample.services.DeptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DeptsController {

    @Autowired
    private DeptsService deptsService;

    @GetMapping
    public List<DeptEntity> findAllDepts(){
        return deptsService.findAllDepts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeptEntity> findDeptById(@PathVariable(value = "id") int id) {
        return deptsService.findDeptById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDept(@RequestBody @Validated DeptEntity dept,
                                                 @PathVariable(value = "id") int id) {
        return deptsService.updateDept(dept, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDept(@PathVariable(value = "id") int id) {
        return deptsService.deleteDept(id);
    }

    @PostMapping
    public ResponseEntity<?> saveDept(@Validated @RequestBody DeptEntity dept) {
        return deptsService.saveDept(dept);
    }

    @GetMapping("/dto")
    public List<DeptDTO> findAllDeptsDTO(){
        return deptsService.findAllDeptsDTO();
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<DeptDTO> findDeptDTOById(@PathVariable(value = "id") int id) {
        return deptsService.findDeptDTOById(id);
    }
}
