package com.web.prog4webapp.controller.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ViewEmployee implements Serializable {
    private String lastName;
    private String firstName;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    private String image;
    private String sex;
    private String address;
    private String personalEmail;
    private String email;
    private String role;
    private int children;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate hire;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate departure;
    private String spc;
    private String cnaps;
    private String phone;
}