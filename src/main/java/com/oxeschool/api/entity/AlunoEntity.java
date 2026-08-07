package com.oxeschool.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "alunos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlunoEntity extends UsuarioEntity {

    @Column(name = "cursos_matriculados")
    private List<Integer> cursosMatriculados;

}
