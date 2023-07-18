package com.web.prog4webapp.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"cnaps\"")
@EqualsAndHashCode
@ToString
@Builder
public class CNAPS {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String cnaps;
}
