package com.web.prog4webapp.service;

import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee restEmployee){
        return employeeRepository.save(restEmployee);
    }
}
