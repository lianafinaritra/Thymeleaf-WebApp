package com.web.prog4webapp.controller.model;

import com.web.prog4webapp.model.Phone;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class CreateCompany implements Serializable {
    private String name;
    private String description;
    private String slogan;
    private String address;
    private String email;
    private String phone;
    private String nif;
    private String stat;
    private String rcs;
}
