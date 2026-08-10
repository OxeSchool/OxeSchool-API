package com.oxeschool.api.domain;

import com.oxeschool.api.entity.Curso.Modulos;
import com.oxeschool.api.entity.StatusCurso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CursoDomain {

    private UUID id;
    private String nome;
    private Long idProfessor;
    private List<Modulos> modulos;

}
