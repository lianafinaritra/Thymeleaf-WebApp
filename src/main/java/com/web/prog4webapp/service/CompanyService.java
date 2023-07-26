package com.web.prog4webapp.service;

import com.web.prog4webapp.controller.model.CreateCompany;
import com.web.prog4webapp.model.Company;
import com.web.prog4webapp.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;
    public Company getCompanyConfiguration() {
        Optional<Company> tempCompany = Optional.ofNullable(repository.findAll().get(0));
        if(tempCompany.isPresent()){
            return tempCompany.get();
        } else {
            return new Company();
        }
    }

    public Company createCompanyConfiguration(Company createCompany) {
        return repository.save(createCompany);
    }
}
