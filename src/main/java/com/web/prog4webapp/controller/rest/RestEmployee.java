package com.web.prog4webapp.controller.rest;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RestEmployee{
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private MultipartFile image;
}
