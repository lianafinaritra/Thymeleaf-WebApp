package com.web.prog4webapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"company\"")
@EqualsAndHashCode
@ToString
@Builder
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private String slogan;
    private String address;
    private String email;
    @OneToOne
    private Phone phone;
    private String nif;
    private String stat;
    private String rcs;
}
