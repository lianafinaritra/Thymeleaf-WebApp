package com.web.prog4webapp.mapper;

import com.web.prog4webapp.controller.model.CreateCompany;
import com.web.prog4webapp.controller.model.CreateEmployee;
import com.web.prog4webapp.controller.model.ViewEmployee;
import com.web.prog4webapp.model.CNAPS;
import com.web.prog4webapp.model.Company;
import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.model.Phone;
import com.web.prog4webapp.repository.CnapsRepository;
import com.web.prog4webapp.repository.EmployeeRepository;
import com.web.prog4webapp.repository.PhoneRepository;
import com.web.prog4webapp.validator.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class EmployeeMapper {
    private EmployeeRepository repository;
    private CnapsRepository cnapsRepository;
    private PhoneRepository phoneRepository;
    public ViewEmployee toRest(Employee domain){
        CNAPS cnaps = domain.getCnaps();
        List list = new ArrayList<>();
        List<Phone> phones = domain.getPhone();
        for (Phone phone : phones) {
            list.add(phone.getPhoneNumber());
        }
        return ViewEmployee.builder()
                .id(domain.getId())
                .userName(domain.getUserName())
                .password(domain.getPassword())
                .matricule(domain.getMatricule())
                .lastName(domain.getLastName())
                .firstName(domain.getFirstName())
                .birthDate(domain.getBirthDate())
                .sex(String.valueOf(domain.getSex()))
                .address(domain.getAddress())
                .personalEmail(domain.getPersonalEmail())
                .email(domain.getEmail())
                .role(domain.getRole())
                .children(domain.getChildren())
                .hire(domain.getHire())
                .departure(domain.getDeparture())
                .spc(String.valueOf(domain.getSpc()))
                .cnaps(cnaps.getCnaps())
                .phone(list)
                .image(domain.getImage())
                .build();
    }

    public Employee toDomain(ViewEmployee viewEmployee){
        Employee newEmployee = new Employee();
        Optional<CNAPS> cnaps = cnapsRepository.findCNAPSByCnaps(viewEmployee.getCnaps());
        if(cnaps.isPresent()){
            newEmployee.setCnaps(cnaps.get());
        }else {
            CNAPS cnaps1 = new CNAPS();
            cnaps1.setCnaps(viewEmployee.getCnaps());
            CNAPS newCnaps = cnapsRepository.save(cnaps1);
            newEmployee.setCnaps(newCnaps);
        }
        List list = new ArrayList<>();
        List<String> phones = viewEmployee.getPhone();
        for (String phoneNumber : phones) {
            Optional<Phone> phoneOptional = phoneRepository.findPhoneByPhoneNumber(phoneNumber);

            if (phoneOptional.isPresent()) {
                throw new BadRequestException("Ce numéro a déjà été déjà utilisé" + phoneOptional);
            } else {
                Phone newPhone = new Phone();
                newPhone.setPhoneNumber(phoneNumber);
                Phone savedPhone = phoneRepository.save(newPhone);
                list.add(savedPhone);
            }
        }
        return Employee.builder()
                .id(viewEmployee.getId())
                .userName(viewEmployee.getUserName())
                .password(viewEmployee.getPassword())
                .image(viewEmployee.getImage())
                .matricule(viewEmployee.getMatricule())
                .lastName(viewEmployee.getLastName())
                .firstName(viewEmployee.getFirstName())
                .birthDate(viewEmployee.getBirthDate())
                .sex(newEmployee.convertStringToSex(viewEmployee.getSex()))
                .address(viewEmployee.getAddress())
                .personalEmail(viewEmployee.getPersonalEmail())
                .email(viewEmployee.getEmail())
                .role(viewEmployee.getRole())
                .children(viewEmployee.getChildren())
                .hire(viewEmployee.getHire())
                .departure(viewEmployee.getDeparture())
                .spc(newEmployee.convertStringToSPC(viewEmployee.getSpc()))
                .phone(list)
                .cnaps(newEmployee.getCnaps())
                .build();
    }
    public Employee toDomain(CreateEmployee createEmployee) throws IOException {
        String mat = "";
        List<Employee> employees = repository.findAll();
        Optional<CNAPS> cnaps = cnapsRepository.findCNAPSByCnaps(createEmployee.getCnaps());
        Employee newEmployee = new Employee();
        List list = new ArrayList<>();
        List<String> phones = createEmployee.getPhone();
        for (String phoneNumber : phones) {
            Optional<Phone> phoneOptional = phoneRepository.findPhoneByPhoneNumber(phoneNumber);

            if (phoneOptional.isPresent()) {
                throw new BadRequestException("Ce numéro a déjà été déjà utilisé" + phoneOptional);
            } else {
                Phone newPhone = new Phone();
                newPhone.setPhoneNumber(phoneNumber);
                Phone savedPhone = phoneRepository.save(newPhone);
                list.add(savedPhone);
            }
        }
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
        if(cnaps.isPresent()){
            throw new BadRequestException("Ce numéro a déjà été déjà utilisé");
        }else {
            CNAPS cnaps1 = new CNAPS();
            cnaps1.setCnaps(createEmployee.getCnaps());
            CNAPS newCnaps = cnapsRepository.save(cnaps1);
            newEmployee.setCnaps(newCnaps);
        }
        MultipartFile file = createEmployee.getImage();
        byte[] bytes = file.getBytes();
        String image = Base64.getEncoder().encodeToString(bytes);
        newEmployee.setUserName(createEmployee.getUserName());
        newEmployee.setPassword(createEmployee.getPassword());
        newEmployee.setLastName(createEmployee.getLastName());
        newEmployee.setFirstName(createEmployee.getFirstName());
        newEmployee.setBirthDate(createEmployee.getBirthDate());
        newEmployee.setImage(image);
        newEmployee.setMatricule(mat);
        newEmployee.setSex(newEmployee.convertStringToSex(createEmployee.getSex()));
        newEmployee.setAddress(createEmployee.getAddress());
        newEmployee.setEmail(createEmployee.getEmail());
        newEmployee.setPersonalEmail(createEmployee.getPersonalEmail());
        newEmployee.setRole(createEmployee.getRole());
        newEmployee.setChildren(createEmployee.getChildren());
        newEmployee.setHire(createEmployee.getHire());
        newEmployee.setDeparture(createEmployee.getDeparture());
        newEmployee.setSpc(newEmployee.convertStringToSPC(createEmployee.getSpc()));
        newEmployee.setPhone(list);
        return newEmployee;
    }

    public Company toDomain(CreateCompany createCompany){
        Optional<Phone> phone = phoneRepository.findPhoneByPhoneNumber(createCompany.getPhone());
        Company newCompany = new Company();
        if(phone.isPresent()){
            throw new BadRequestException("Ce numéro a déjà été déjà utilisé");
        }else {
            Phone phone1 = new Phone();
            phone1.setPhoneNumber(createCompany.getPhone());
            Phone newPhone = phoneRepository.save(phone1);
            newCompany.setPhone(newPhone);
        }
        newCompany.setName(createCompany.getName());
        newCompany.setDescription(createCompany.getDescription());
        newCompany.setSlogan(createCompany.getSlogan());
        newCompany.setAddress(createCompany.getAddress());
        newCompany.setEmail(createCompany.getEmail());
        newCompany.setNif(createCompany.getNif());
        newCompany.setStat(createCompany.getStat());
        newCompany.setRcs(createCompany.getRcs());
        return newCompany;
    }
}
