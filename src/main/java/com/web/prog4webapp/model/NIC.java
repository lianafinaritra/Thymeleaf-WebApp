package com.web.prog4webapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"nic\"")
@EqualsAndHashCode
@ToString
@Builder
public class NIC {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String number;
    private LocalDate date;
    private String delivery;
}
