package com.web.prog4webapp.controller.rest;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RestEmployee implements Serializable {
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private MultipartFile image;
}
