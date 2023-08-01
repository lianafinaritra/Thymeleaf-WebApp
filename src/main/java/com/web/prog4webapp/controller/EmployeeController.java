package com.web.prog4webapp.controller;

import com.web.prog4webapp.controller.model.CreateCompany;
import com.web.prog4webapp.controller.model.CreateEmployee;
import com.web.prog4webapp.controller.model.Credentials;
import com.web.prog4webapp.controller.model.ViewEmployee;
import com.web.prog4webapp.mapper.EmployeeMapper;
import com.web.prog4webapp.model.Company;
import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.model.Session;
import com.web.prog4webapp.service.CompanyService;
import com.web.prog4webapp.service.EmployeeService;
import com.web.prog4webapp.service.SessionService;
import com.web.prog4webapp.validator.EmployeeValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@CrossOrigin
public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;
    private final SessionService sessionService;
    private final CompanyService companyService;
    private final EmployeeValidator validator;
    @GetMapping("/")
    public String LoginPage(Model model) {
        model.addAttribute("credentials", Credentials.builder().build());
        return "login";
    }

    @PostMapping("/postCredentials")
    public String LoginUser(@ModelAttribute("credentials") Credentials credentials, HttpServletRequest request){
        Employee employee = service.authenticate(credentials.getUserName(), credentials.getPassword());
        if(employee != null){
            Session session1 = new Session();
            session1.setEmployee(employee);
            Date currentDate = new Date();
            LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate tomorrow = localDate.plusDays(1);
            session1.setValidate(tomorrow);
            Session session = sessionService.createOrUpdateSession(session1);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("sessionId", session.getId());
            return "redirect:/list";
        }else {
            return "redirect:/";
        }
    }

    @GetMapping("/form")
    public String FormPage(Model model) {
        model.addAttribute("employee", CreateEmployee.builder().build());
        return "form";
    }
    @GetMapping("/details")
    public String EmployeePage(@RequestParam("id") String id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        Session domainSession = sessionService.getSessionById(sessionId);
        Company company = companyService.getCompanyConfiguration();
        if (domainSession.getId() != null){
            Employee employee = service.getEmployeeById(id);
            ViewEmployee viewEmployee = mapper.toRest(employee);
            model.addAttribute("employee", viewEmployee);
            model.addAttribute("company", company);
            return "details";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/search")
    public String SearchPage(@RequestParam("word") String word, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        Session domainSession = sessionService.getSessionById(sessionId);
        if (domainSession.getId() != null){
            List<Employee> employees = service.searchByWord(word);
            model.addAttribute("employees", employees);
            model.addAttribute("sessionId", sessionId);
            return "list";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/searchBySex")
    public String SearchBySex(@RequestParam("sex") String sex, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        Session domainSession = sessionService.getSessionById(sessionId);
        if (domainSession.getId() != null){
            List<Employee> employees = service.searchBySex(sex);
            model.addAttribute("employees", employees);
            model.addAttribute("sessionId", sessionId);
            return "list";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/searchByCode")
    public String SearchByCode(@RequestParam("code") String code, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        Session domainSession = sessionService.getSessionById(sessionId);
        if (domainSession.getId() != null){
            List<Employee> employees = service.searchByCode(code);
            model.addAttribute("employees", employees);
            model.addAttribute("sessionId", sessionId);
            return "list";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/sort")
    public String SortPage(@RequestParam(value = "sortAttribute", defaultValue = "lastName") String sortAttribute,
                           @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
                           Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        Session domainSession = sessionService.getSessionById(sessionId);
        if (domainSession.getId() != null){
            List<Employee> employees = service.sort(sortOrder, sortAttribute);
            model.addAttribute("employees", employees);
            model.addAttribute("sessionId", sessionId);
            return "list";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/list")
    public String ListPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        Session domainSession = sessionService.getSessionById(sessionId);
        Company company = companyService.getCompanyConfiguration();
        if (domainSession.getId() != null){
            List<Employee> employees = service.getAllEmployees();
            model.addAttribute("employees", employees);
            model.addAttribute("sessionId", sessionId);
            model.addAttribute("company", company);
            return "list";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/postEmployee")
    public String createEmployee(@ModelAttribute("employee") CreateEmployee newEmployee) throws Exception {
        validator.validate(newEmployee);
        service.createOrUpdateEmployee(mapper.toDomain(newEmployee));
        return "redirect:/list";
    }

    @PostMapping("/company")
    public Company createCompany(@RequestParam String name, @RequestParam String description, @RequestParam String slogan,@RequestParam String address,@RequestParam String email,@RequestParam String phone,@RequestParam String nif,@RequestParam String stat,@RequestParam String rcs){
        CreateCompany company = CreateCompany.builder()
                .name(name)
                .description(description)
                .slogan(slogan)
                .address(address)
                .email(email)
                .phone(phone)
                .nif(nif)
                .stat(stat)
                .rcs(rcs)
                .build();
        return companyService.createCompanyConfiguration(mapper.toDomain(company));
    }
    @GetMapping("/modify")
    public String ModificationPage(@RequestParam("employeeId") String id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        Session domainSession = sessionService.getSessionById(sessionId);
        if (domainSession.getId() != null){
            Employee emp = service.getEmployeeById(id);
            ViewEmployee employee = mapper.toRest(emp);
            model.addAttribute("employee", employee);
            return "modify";
        } else {
            return "redirect:/";
        }
    }
    @PostMapping("/modifyEmployee")
    public String updateEmployee(@ModelAttribute("employee") ViewEmployee viewEmployee, Model model) {
        service.createOrUpdateEmployee(mapper.toDomain(viewEmployee));
        return "redirect:/list";
    }
    @PostMapping("/disconnect")
    public String Disconnect(@ModelAttribute("sessionId") String sessionId){
        sessionService.disconnect(sessionId);
        return "redirect:/";
    }
    @GetMapping("/export")
    public void exportToCSV(@RequestParam("employeeId") String id, HttpServletResponse response) {
        try {
            Employee employee = service.getEmployeeById(id);

            List<String> data = new ArrayList<>();
            data.add("Matricule :," + employee.getMatricule());
            data.add("Prénom :," + employee.getFirstName());
            data.add("Nom :," + employee.getLastName());
            data.add("Anniversaire :," + employee.getBirthDate());
            data.add("Adresse :," + employee.getAddress());
            data.add("Fonction :," + employee.getRole());
            data.add("Date d'embauche :," + employee.getHire());
            data.add("Email :," + employee.getEmail());

            String fileName = "Fiche_" + employee.getFirstName() + "_" + employee.getLastName() + ".csv";
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            PrintWriter writer = response.getWriter();

            for (String row : data) {
                writer.println(row);
            }

            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
