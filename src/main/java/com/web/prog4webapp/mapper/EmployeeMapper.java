package com.web.prog4webapp.mapper;

import com.web.prog4webapp.controller.rest.RestEmployee;
import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Component
@AllArgsConstructor
public class EmployeeMapper {
    private EmployeeRepository repository;
    public Employee toDomain(RestEmployee restEmployee) throws IOException {
        String mat = "";
        List<Employee> employees = repository.findAll();
        if(employees.size() > 0){
            Employee employee = repository.findById(employees.get(employees.size() - 1).getId()).get();
            String current = employee.getMatricule();
            String lastThreeDigitsStr = current.substring(3);
            int lastThreeDigits = Integer.parseInt(lastThreeDigitsStr);
            int newLastThreeDigits = lastThreeDigits + 1;
            mat = current.substring(0, 3) + newLastThreeDigits;;
        } else {
            mat = "EMP14001";
        }
        MultipartFile file = restEmployee.getImage();
        byte[] bytes = file.getBytes();
        String image = Base64.getEncoder().encodeToString(bytes);
        Employee newEmployee = new Employee();
        newEmployee.setLastName(restEmployee.getLastName());
        newEmployee.setFirstName(restEmployee.getFirstName());
        newEmployee.setBirthDate(restEmployee.getBirthDate());
        newEmployee.setImage(image);
        newEmployee.setMatricule(mat);
        return newEmployee;
    }
}
