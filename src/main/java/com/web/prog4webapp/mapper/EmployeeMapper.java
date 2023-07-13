package com.web.prog4webapp.mapper;

import com.web.prog4webapp.model.Employee;
import lombok.AllArgsConstructor;
import org.hibernate.boot.model.relational.Sequence;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class EmployeeMapper {

    public Employee toDomain(String lastName, String firstName, LocalDate birthDate, String image) {
        Employee newEmployee = new Employee();
        newEmployee.setLastName(lastName);
        newEmployee.setFirstName(firstName);
        newEmployee.setBirthDate(birthDate);
        newEmployee.setImage(image);
        newEmployee.setMatricule("EMP");
        return newEmployee;
    }
}
