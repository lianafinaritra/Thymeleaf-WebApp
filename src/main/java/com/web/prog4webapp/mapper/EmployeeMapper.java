package com.web.prog4webapp.mapper;

import com.web.prog4webapp.controller.model.CreateEmployee;
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
        List<Phone> phone = domain.getPhone();
        return ViewEmployee.builder()
                .id(domain.getId())
                .userName(domain.getUserName())
                .password(domain.getPassword())
                .sessionId(domain.getSessionId())
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

    public Employee toDomain(ViewEmployee viewEmployee){
        Employee newEmployee = new Employee();
        Optional<Phone> phone = phoneRepository.findPhoneByPhoneNumber(viewEmployee.getPhone());
        List<Phone> list = new ArrayList<>();
        Phone newPhone = new Phone();
        Optional<CNAPS> cnaps = cnapsRepository.findCNAPSByCnaps(viewEmployee.getCnaps());
        if(cnaps.isPresent()){
            newEmployee.setCnaps(cnaps.get());
        }else {
            CNAPS cnaps1 = new CNAPS();
            cnaps1.setCnaps(viewEmployee.getCnaps());
            CNAPS newCnaps = cnapsRepository.save(cnaps1);
            newEmployee.setCnaps(newCnaps);
        }
       if(phone.isPresent()){
           list.add(phone.get());
       } else {
           Phone phone1 = new Phone();
           phone1.setPhoneNumber(viewEmployee.getPhone());
           Phone phone2 = phoneRepository.save(phone1);
           newPhone.setId(phone2.getId());
           newPhone.setPhoneNumber(phone2.getPhoneNumber());
           list.add(newPhone);
       }
        return Employee.builder()
                .id(viewEmployee.getId())
                .userName(viewEmployee.getUserName())
                .password(viewEmployee.getPassword())
                .sessionId(viewEmployee.getSessionId())
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
        Optional<Phone> phone = phoneRepository.findPhoneByPhoneNumber(createEmployee.getPhone());
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
            cnaps1.setCnaps(createEmployee.getCnaps());
            CNAPS newCnaps = cnapsRepository.save(cnaps1);
            newEmployee.setCnaps(newCnaps);
        }if(phone.isPresent()){
            List list = new ArrayList<>();
            list.add(phone.get());
            newEmployee.setPhone(list);
        }else {
            Phone phone1 = new Phone();
            phone1.setPhoneNumber(createEmployee.getPhone());
            Phone newPhone = phoneRepository.save(phone1);
            List list = new ArrayList<>();
            list.add(newPhone);
            newEmployee.setPhone(list);
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
        return newEmployee;
    }
}
