package com.oxeschool.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "professores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorEntity extends UsuarioEntity {

    @Column(name = "cursos_lecionados")
    private List<Integer> cursosMinistrados;

}
