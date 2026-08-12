package com.oxeschool.api.dtos.curso;


import com.oxeschool.api.entity.Curso.Modulo;
import com.oxeschool.api.enums.StatusCurso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CursoResponse {

    private UUID id;
    private Long idProfessor;
    private String nome;
    private String descricao;
    private String categoria;
    private Long cargaHoraria;
    private List<Modulo> modulos;
    private StatusCurso status;

}
