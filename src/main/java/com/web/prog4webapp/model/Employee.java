package com.web.prog4webapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@ToString
@Builder
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String matricule;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    @Lob
    private String image;
}
