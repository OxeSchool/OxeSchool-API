package com.oxeschool.api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "alunos")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AlunoEntity extends UsuarioEntity {
    // Alterações futuras -> campos relacionados aos epics de matricula e cursos
}