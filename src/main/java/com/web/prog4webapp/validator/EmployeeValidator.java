package com.web.prog4webapp.validator;

import com.web.prog4webapp.controller.model.CreateEmployee;
import com.web.prog4webapp.validator.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class EmployeeValidator {
    public void validate(CreateEmployee employee) throws BadRequestException {
        StringBuilder error = new StringBuilder();

        if (employee.getBirthDate().isAfter(LocalDate.now())) {
            error.append("Date d'anniversaire Invalide ");
        }
        if (employee.getPhone() == null || employee.getPhone().isEmpty()) {
            error.append("Vous devez au moins avoir un numéro de téléphone ");
        } else {
            try {
                for (String phone : employee.getPhone()) {
                    String[] parts = phone.split("\\s+");
                    if (parts.length != 2 || parts[1].length() != 10 || !parts[1].matches("\\d+")) {
                        error.append("Le numéro doit etre composer de 10 chiffres: ").append(phone).append(". ");
                    }
                }
            } catch (BadRequestException e){
                error.append(e.getMessage());
            }
        }
        if (!error.isEmpty()) {
            throw new BadRequestException(error.toString());
        }
    }
}
