package com.web.prog4webapp.controller;

import com.web.prog4webapp.controller.rest.RestEmployee;
import com.web.prog4webapp.mapper.EmployeeMapper;
import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@CrossOrigin
public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;
    @GetMapping("/")
    public String HomePage(Model model) {
        List<Employee> employees = service.getAllEmployees();
        model.addAttribute("employees", employees);
        return "home";
    }
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @PostMapping("/employee")
    public String createEmployee(@ModelAttribute("employee") RestEmployee newEmployee) throws IOException {
        service.createEmployee(mapper.toDomain(newEmployee));
        return "home";
    }

}
