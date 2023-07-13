package com.web.prog4webapp.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RestEmployee{
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private MultipartFile image;
}
