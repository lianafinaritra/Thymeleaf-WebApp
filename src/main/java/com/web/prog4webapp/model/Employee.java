package com.web.prog4webapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"employee\"")
@EqualsAndHashCode
@ToString
@Builder
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String matricule;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    @Lob
    private String image;
    private Sex sex;
    @OneToMany
    private List<Phone> phone;
    private String address;
    private String personalEmail;
    private String email;
    @OneToOne
    private NIC nic;
    private String role;
    private Integer children;
    private LocalDate hire;
    private LocalDate departure;
    private SPC spc;
    @OneToOne
    private CNAPS cnaps;
    enum Sex {
        H,
        F
    }
    enum SPC {
        M1,M2,OS1, OS2, OS3, OP1
    }
}
