package com.web.prog4webapp.service;

import com.web.prog4webapp.controller.model.ViewEmployee;
import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }
    public Employee getEmployeeById(String employee_id){
        Optional<Employee> employee = repository.findById(employee_id);
        if(employee.isPresent()){
            Employee current = employee.get();
            return current;
        }
        return new Employee();
    }

    public Employee createOrUpdateEmployee(Employee restEmployee){
        return repository.save(restEmployee);
    }

    @Transactional

    public List<Employee> searchByWord(String word) {
        return repository.searchByWord(word);
    }

    public List<Employee> sort(String sortOrder, String atr) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(atr).ascending() :
                Sort.by(atr).descending();

        return repository.findAll(sort);
    }
}
