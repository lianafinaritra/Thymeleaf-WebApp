package com.web.prog4webapp.controller.model;

import com.web.prog4webapp.model.Phone;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class ViewEmployee implements Serializable {
    private String id;
    private String userName;
    private String password;
    private String matricule;
    private String lastName;
    private String firstName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String image;
    private String sex;
    private String address;
    private String personalEmail;
    private String email;
    private String role;
    private int children;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hire;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departure;
    private String spc;
    private String cnaps;
    private List<String> phone;
}
