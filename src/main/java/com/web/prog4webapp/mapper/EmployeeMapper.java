package com.web.prog4webapp.mapper;

import com.web.prog4webapp.controller.model.RestEmployee;
import com.web.prog4webapp.controller.model.ViewEmployee;
import com.web.prog4webapp.model.CNAPS;
import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.model.Phone;
import com.web.prog4webapp.repository.CnapsRepository;
import com.web.prog4webapp.repository.EmployeeRepository;
import com.web.prog4webapp.repository.PhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
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
    public ViewEmployee toRest(Employee domain) throws IOException {
        CNAPS cnaps = domain.getCnaps();
        List<Phone> phone = domain.getPhone();
        return ViewEmployee.builder()
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
                .phone(phone.get(0).getPhoneNumber())
                .image(domain.getImage())
                .build();
    }
    public Employee toDomain(RestEmployee restEmployee) throws IOException {
        String mat = "";
        List<Employee> employees = repository.findAll();
        Optional<CNAPS> cnaps = cnapsRepository.findCNAPSByCnaps(restEmployee.getCnaps());
        Optional<Phone> phone = phoneRepository.findPhoneByPhoneNumber(restEmployee.getPhone());
        Employee newEmployee = new Employee();
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
            newEmployee.setCnaps(cnaps.get());
        }else {
            CNAPS cnaps1 = new CNAPS();
            cnaps1.setCnaps(restEmployee.getCnaps());
            CNAPS newCnaps = cnapsRepository.save(cnaps1);
            newEmployee.setCnaps(newCnaps);
        }if(phone.isPresent()){
            List list = new ArrayList<>();
            list.add(phone.get());
            newEmployee.setPhone(list);
        }else {
            Phone phone1 = new Phone();
            phone1.setPhoneNumber(restEmployee.getPhone());
            Phone newPhone = phoneRepository.save(phone1);
            List list = new ArrayList<>();
            list.add(newPhone);
            newEmployee.setPhone(list);
        }
        MultipartFile file = restEmployee.getImage();
        byte[] bytes = file.getBytes();
        String image = Base64.getEncoder().encodeToString(bytes);
        newEmployee.setLastName(restEmployee.getLastName());
        newEmployee.setFirstName(restEmployee.getFirstName());
        newEmployee.setBirthDate(restEmployee.getBirthDate());
        newEmployee.setImage(image);
        newEmployee.setMatricule(mat);
        newEmployee.setSex(newEmployee.convertStringToSex(restEmployee.getSex()));
        newEmployee.setAddress(restEmployee.getAddress());
        newEmployee.setEmail(restEmployee.getEmail());
        newEmployee.setPersonalEmail(restEmployee.getPersonalEmail());
        newEmployee.setRole(restEmployee.getRole());
        newEmployee.setChildren(restEmployee.getChildren());
        newEmployee.setHire(restEmployee.getHire());
        newEmployee.setDeparture(restEmployee.getDeparture());
        newEmployee.setSpc(newEmployee.convertStringToSPC(restEmployee.getSpc()));
        return newEmployee;
    }
}
