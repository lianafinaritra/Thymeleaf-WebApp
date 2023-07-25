package com.web.prog4webapp.controller;

import com.web.prog4webapp.controller.model.CreateEmployee;
import com.web.prog4webapp.controller.model.ViewEmployee;
import com.web.prog4webapp.mapper.EmployeeMapper;
import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@CrossOrigin
public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;
    @GetMapping("/login")
    public String LoginPage(Model model) {
        model.addAttribute("employee", CreateEmployee.builder().build());
        return "login";
    }

    @GetMapping("/")
    public String FormPage(Model model) {
        model.addAttribute("employee", CreateEmployee.builder().build());
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
    public String createEmployee(@ModelAttribute("employee") CreateEmployee newEmployee, Model model) throws IOException {
        service.createOrUpdateEmployee(mapper.toDomain(newEmployee));
        List<Employee> employees = service.getAllEmployees();
        model.addAttribute("employees", employees);
        return "list";
    }
    @GetMapping("/modify")
    public String ModificationPage(@RequestParam("employeeId") String id, Model model) throws IOException {
        Employee emp = service.getEmployeeById(id);
        ViewEmployee employee = mapper.toRest(emp);
        model.addAttribute("employee", employee);
        return "modify";
    }
    @PostMapping("/modifyEmployee")
    public String updateEmployee(@ModelAttribute("employee") ViewEmployee viewEmployee, Model model) {
        service.createOrUpdateEmployee(mapper.toDomain(viewEmployee));
        List<Employee> employees = service.getAllEmployees();
        model.addAttribute("employees", employees);
        return "list";
    }
    @GetMapping("/export")
    public void exportToCSV(@RequestParam("employeeId") String id, HttpServletResponse response) {
        try {
            Employee employee = service.getEmployeeById(id);

            List<String> data = new ArrayList<>();
            data.add("Matricule :," + employee.getMatricule());
            data.add("Prénom :," + employee.getFirstName());
            data.add("Nom :," + employee.getLastName());
            data.add("Anniversaire :," + employee.getBirthDate());
            data.add("Adresse :," + employee.getAddress());
            data.add("Fonction :," + employee.getRole());
            data.add("Date d'embauche :," + employee.getHire());
            data.add("Email :," + employee.getEmail());

            String fileName = "Fiche_" + employee.getFirstName() + employee.getLastName() + ".csv";
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            PrintWriter writer = response.getWriter();

            for (String row : data) {
                writer.println(row);
            }

            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
