package com.web.prog4webapp.controller.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreateEmployee implements Serializable {
    private String userName;
    private String password;
    private String lastName;
    private String firstName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private MultipartFile image;
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
    private String phone;
}
