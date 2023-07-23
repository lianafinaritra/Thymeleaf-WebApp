package com.web.prog4webapp.controller;

import com.web.prog4webapp.controller.model.RestEmployee;
import com.web.prog4webapp.controller.model.ViewEmployee;
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
    public String FormPage(Model model) {
        model.addAttribute("employee", RestEmployee.builder().build());
        return "form";
    }
    @GetMapping("/details")
    public String EmployeePage(@RequestParam("id") String id, Model model) throws IOException {
        Employee employee = service.getEmployeeById(id);
        ViewEmployee viewEmployee = mapper.toRest(employee);
        model.addAttribute("employee", viewEmployee);
        return "details";
    }

    @GetMapping("/search")
    public String SearchPage(@RequestParam("word") String word, Model model){
        List<Employee> employees = service.searchByWord(word);
        model.addAttribute("employees", employees);
        return "list";
    }
    @GetMapping("/sort")
    public String SortPage(@RequestParam(value = "sortAttribute", defaultValue = "lastName") String sortAttribute,
                           @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
                           Model model) {
        List<Employee> employees = service.sort(sortOrder, sortAttribute);
        model.addAttribute("employees", employees);
        return "list";
    }
    @GetMapping("/list")
    public String ListPage(Model model){
        List<Employee> employees = service.getAllEmployees();
        model.addAttribute("employees", employees);
        return "list";
    }
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @PostMapping("/postEmployee")
    public String createEmployee(@ModelAttribute("employee") RestEmployee newEmployee, Model model) throws IOException {
        service.createEmployee(mapper.toDomain(newEmployee));
        List<Employee> employees = service.getAllEmployees();
        model.addAttribute("employees", employees);
        return "list";
    }
}
