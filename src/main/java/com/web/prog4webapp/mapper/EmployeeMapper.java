package com.web.prog4webapp.mapper;

import com.web.prog4webapp.controller.rest.RestEmployee;
import com.web.prog4webapp.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Component
@AllArgsConstructor
public class EmployeeMapper {

    public Employee toDomain(RestEmployee restEmployee) throws IOException {
        MultipartFile file = restEmployee.getImage();
        byte[] bytes = file.getBytes();
        String image = Base64.getEncoder().encodeToString(bytes);
        Employee newEmployee = new Employee();
        newEmployee.setLastName(restEmployee.getLastName());
        newEmployee.setFirstName(restEmployee.getFirstName());
        newEmployee.setBirthDate(restEmployee.getBirthDate());
        newEmployee.setImage(image);
        return newEmployee;
    }
}
