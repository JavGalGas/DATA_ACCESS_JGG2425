package com.jgg2425.da.unit5.springemployeeexample.controllers;

import com.jgg2425.da.unit5.springemployeeexample.models.dao.IDeptEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.dao.IEmployeeEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.dto.EmployeeDTO;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.DeptEntity;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.EmployeeEntity;
import com.jgg2425.da.unit5.springemployeeexample.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private IDeptEntityDAO deptEntityDAO;

    @Autowired
    private EmployeesService employeesService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/seedepartments")
    public String showDepts(Model model) {
        List<DeptEntity> depts = (List<DeptEntity>) deptEntityDAO.findAll();
        model.addAttribute("depts", depts);
        return "seedepartments";
    }

    @GetMapping("/seeemployees")
    public String showEmployees(Model model) {
        List<EmployeeDTO> employees = employeesService.findAllEmployeesDTO();
        model.addAttribute("employees", employees);
        return "seeemployees";
    }

    @GetMapping("/departmentdischarge")
    public String departmentDischarge(Model model) {
        model.addAttribute("dept", new DeptEntity());
        return "departmentdischarge";
    }

    @PostMapping("/departmentdischarge")
    public String createDept(@ModelAttribute DeptEntity dept, Model model) {
        if (!deptEntityDAO.existsById(dept.getId())) {
            deptEntityDAO.save(dept);
            model.addAttribute("message", "Department created successfully");
        }
        else {
            model.addAttribute("message", "Error creating department: " + dept.getId());
        }
        return "departmentdischarge";
    }
}
