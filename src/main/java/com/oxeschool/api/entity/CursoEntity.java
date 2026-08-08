package com.oxeschool.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    @Column(name = "professor_id")
    private Long professorId;
    private String categoria;
    @Column(name = "carga_horaria")
    private Integer cargaHoraria;
    @Enumerated(EnumType.STRING)
    private StatusCurso status;

}
