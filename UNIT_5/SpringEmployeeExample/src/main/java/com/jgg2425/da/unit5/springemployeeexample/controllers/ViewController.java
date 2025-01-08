package com.jgg2425.da.unit5.springemployeeexample.controllers;

import com.jgg2425.da.unit5.springemployeeexample.models.dao.IDeptEntityDAO;
import com.jgg2425.da.unit5.springemployeeexample.models.entities.DeptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private IDeptEntityDAO deptEntityDAO;

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
}
