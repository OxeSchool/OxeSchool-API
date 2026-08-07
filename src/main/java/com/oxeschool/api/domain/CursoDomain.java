package com.oxeschool.api.domain;

import com.oxeschool.api.entity.StatusCurso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CursoDomain {

    private Long id;
    private String nome;
    private String descricao;
    private Long professorId;
    private String categoria;
    private Integer cargaHoraria;
    private StatusCurso status;

}
