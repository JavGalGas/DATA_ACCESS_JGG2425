package com.jgg2425.da.unit5.springemployeeexample.controllers;

import com.jgg2425.da.unit5.springemployeeexample.models.dao.IDeptEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.dao.IEmployeeEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.DeptEntity;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class ViewController {

    @Autowired
    private IDeptEntityDAO deptEntityDAO;

    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/viewdepartments")
    public String showDepts(Model model) {
        List<DeptEntity> depts = (List<DeptEntity>) deptEntityDAO.findAll();
        model.addAttribute("depts", depts);
        return "viewdepartments";
    }

    @GetMapping("/viewdepartment")
    public String showDept(Model model, @RequestParam(name = "id", required = true) int id) {
        Optional<DeptEntity> department = deptEntityDAO.findById(id);
        if(department.isEmpty()) {
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Department with id " + id + " was not found");
            return "error";
        }
        model.addAttribute("department", department.get());
        return "viewdepartment";
    }

    @GetMapping("/viewemployees")
    public String showEmployees(Model model, @RequestParam(name="deptno", required = false) Integer deptno) {
        List<DeptEntity> depts = (List<DeptEntity>) deptEntityDAO.findAll();
        model.addAttribute("departments", depts);
        List<EmployeeEntity> employees;
        DeptEntity selectedDept = null;

        if (deptno == null) {
            employees = (List<EmployeeEntity>) employeeEntityDAO.findAll();
        }
        else {
            employees = employeeEntityDAO.findByDeptno_Id(deptno);
            selectedDept = deptEntityDAO.findById(deptno).orElse(null);
        }
        model.addAttribute("employees", employees);
        model.addAttribute("selectedDept", selectedDept);
        return "viewemployees";
    }

    @GetMapping("/viewemployee")
    public String showEmployee(Model model, @RequestParam(name = "id", required = true) int id) {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);
        if(employee.isEmpty()) {
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Employee with id " + id + " was not found");
            return "error";
        }
        model.addAttribute("employee", employee.get());
        return "viewemployee";
    }

    @GetMapping("/departmentdischarge")
    public String departmentDischarge(Model model) {
        model.addAttribute("dept", new DeptEntity());
        return "departmentdischarge";
    }

    @PostMapping("/departmentdischarge")
    public String createDept(@ModelAttribute DeptEntity dept, Model model) {
        if (!deptEntityDAO.existsById(dept.getId())) {
            dept.setDname(dept.getDname().toUpperCase());
            dept.setLoc(dept.getLoc().toUpperCase());
            deptEntityDAO.save(dept);
            model.addAttribute("message", "Department created successfully");
            model.addAttribute("theme", "success");
        }
        else {
            model.addAttribute("message", "Error: Department with Id " + dept.getId() + " already exists");
            model.addAttribute("theme", "error");
        }
        return "departmentdischarge";
    }

    @GetMapping("/employeedischarge")
    public String employeeDischarge(Model model) {
        model.addAttribute("employee", new EmployeeEntity());
        return "employeedischarge";
    }

    @PostMapping("/employeedischarge")
    public String createEmployee(@ModelAttribute EmployeeEntity employee, @RequestParam("deptId") int deptId, Model model) {
        var departmentOptional = deptEntityDAO.findById(deptId);
        if (departmentOptional.isEmpty()) {
            model.addAttribute("message", "Error: Department with ID " + deptId + " doesn't exists.");
            model.addAttribute("theme", "error");
        } else if (employeeEntityDAO.existsById(employee.getId())) {
            model.addAttribute("message", "Error: Employee with ID " + employee.getId() + " already exists.");
            model.addAttribute("theme", "error");
        } else {
            employee.setEname(employee.getEname().toUpperCase());
            employee.setJob(employee.getJob().toUpperCase());
            employee.setDeptno(departmentOptional.get());
            employeeEntityDAO.save(employee);

            Set<EmployeeEntity> employeeSet = departmentOptional.get().getEmployees();
            employeeSet.add(employee);
            departmentOptional.get().setEmployees(employeeSet);
            deptEntityDAO.save(departmentOptional.get());

            model.addAttribute("message", "Employee created successfully with Department " + deptId + ".");
            model.addAttribute("theme", "success");
        }
        return "employeedischarge";
    }
}
