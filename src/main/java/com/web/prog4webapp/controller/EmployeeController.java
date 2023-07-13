package com.web.prog4webapp.controller;

import com.web.prog4webapp.mapper.EmployeeMapper;
import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@CrossOrigin
public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;
    @GetMapping("/")
    public String HomePage() {
        return "home";
    }
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @PostMapping("/employee")
    public Employee createEmployee(@RequestParam("lastname") String lastname, @RequestParam("firstname") String firstname, @RequestParam("birthdate") LocalDate birthdate, @RequestParam("birthdate") MultipartFile image) throws IOException {
        byte[] file = image.getBytes();
        String encodedString = Base64.encodeBase64String(file);
        return service.createEmployee(mapper.toDomain(lastname, firstname, birthdate, encodedString));
    }

}
