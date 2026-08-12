package com.oxeschool.api.dtos.curso;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CriarCursoRequest {

    private String nome;
    private String descricao;
    private Long idProfessor;
    private String categoria;
    private Long cargaHoraria;

}
