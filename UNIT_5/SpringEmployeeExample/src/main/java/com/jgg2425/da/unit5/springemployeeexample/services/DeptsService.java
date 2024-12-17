package com.jgg2425.da.unit5.springemployeeexample.services;

import com.jgg2425.da.unit5.springemployeeexample.models.dao.IDeptEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.dao.IEmployeeEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.dto.DeptDTO;
import com.jgg2425.da.unit5.springemployeeexample.models.dto.ManagerDeptDTO;
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
public class DeptsService {

    @Autowired
    private IDeptEntityDAO deptEntityDAO;

    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;

    private List<String> relevantJobs = List.of("manager", "president");


    public List<DeptEntity> findAllDepts(){
        return (List<DeptEntity>) deptEntityDAO.findAll();
    }

    public ResponseEntity<DeptEntity> findDeptById(int id) {
        Optional<DeptEntity> dept = deptEntityDAO.findById(id);
        if (dept.isPresent()) {
            return ResponseEntity.ok().body(dept.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateDept(DeptEntity dept,
                                        int id) {
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

    public ResponseEntity<?> deleteDept(int id) {
        Optional<DeptEntity> deptEntity = deptEntityDAO.findById(id);
        if (deptEntity.isPresent()) {
            deptEntityDAO.delete(deptEntity.get());
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> saveDept(DeptEntity dept) {
        if (deptEntityDAO.existsById(dept.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Department already exists.");
        } else {
            deptEntityDAO.save(dept);
            return ResponseEntity.status(HttpStatus.CREATED).body("Department saved successfully.");
        }
    }

    public List<DeptDTO> findAllDeptsDTO() {
        List<DeptEntity> depts = (List<DeptEntity>) deptEntityDAO.findAll();
        List<DeptDTO> deptsDTO = new ArrayList<>();
        for (DeptEntity dept : depts) {
            DeptDTO deptDTO = new DeptDTO();
            deptDTO.setId(dept.getId());
            deptDTO.setDname(dept.getDname());
            deptDTO.setLoc(dept.getLoc());
            deptsDTO.add(deptDTO);
        }
        return deptsDTO;
    }

    public ResponseEntity<DeptDTO> findDeptDTOById(int id) {
        Optional<DeptEntity> dept = deptEntityDAO.findById(id);
        if (dept.isPresent()) {
            DeptDTO deptDTO = new DeptDTO();
            deptDTO.setId(dept.get().getId());
            deptDTO.setDname(dept.get().getDname());
            deptDTO.setLoc(dept.get().getLoc());
            return ResponseEntity.ok().body(deptDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<ManagerDeptDTO> findAllManagerDeptsDTO() {
        List<DeptEntity> depts = (List<DeptEntity>) deptEntityDAO.findAll();
        return depts.stream().map(dept -> {
            ManagerDeptDTO deptDTO = new ManagerDeptDTO();

            deptDTO.setDeptCode(dept.getId());
            deptDTO.setDeptName(dept.getDname());
            deptDTO.setLocation(dept.getLoc());
            deptDTO.setNumEmployees(dept.getEmployees().size());

            List<EmployeeEntity> employees = employeeEntityDAO.findByJobInAndDeptno(relevantJobs, dept.getId());

            employees.stream().findFirst()
                    .ifPresentOrElse(
                            emp -> deptDTO.setBossId(emp.getId()),
                            () -> deptDTO.setBossId(0)
                    );
            return deptDTO;
        }).toList();
    }
}
