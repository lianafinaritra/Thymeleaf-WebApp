package com.web.prog4webapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"session\"")
@EqualsAndHashCode
@ToString
@Builder
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    private Employee employee;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate validate;
}
