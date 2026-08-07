package com.oxeschool.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "professores")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;

}
