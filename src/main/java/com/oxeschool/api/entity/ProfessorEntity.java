package com.oxeschool.api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "professores")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ProfessorEntity extends UsuarioEntity {
    // Alterações futuras -> campos relacionados ao epic de cursos (FK)
}
