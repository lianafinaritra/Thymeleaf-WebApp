package com.web.prog4webapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @Lob
    private String image;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @OneToMany
    private List<Phone> phone;
    private String address;
    private String personalEmail;
    private String email;
    /*@OneToOne
    private NIC nic;*/
    private String role;
    private int children;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hire;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departure;
    @Enumerated(EnumType.STRING)
    private SPC spc;
    @OneToOne
    private CNAPS cnaps;
    enum Sex {
        H,
        F
    }
    public enum SPC {
        M1,M2,OS1, OS2, OS3, OP1
    }
    public Sex convertStringToSex(String sexStr) {
        if ("H".equals(sexStr)) {
            return Sex.H;
        } else if ("F".equals(sexStr)) {
            return Sex.F;
        } else {
            throw new IllegalArgumentException("Invalid sex value: " + sexStr);
        }
    }
    public SPC convertStringToSPC(String spcStr) {
        Map<String, SPC> spcMap = new HashMap<>();
        spcMap.put("M1", SPC.M1);
        spcMap.put("M2", SPC.M2);
        spcMap.put("OS1", SPC.OS1);
        spcMap.put("OS2", SPC.OS2);
        spcMap.put("OS3", SPC.OS3);
        spcMap.put("OP1", SPC.OP1);

        SPC result = spcMap.get(spcStr);
        if (result == null) {
            throw new IllegalArgumentException("Invalid spc value: " + spcStr);
        }

        return result;
    }
}
