package com.oxeschool.api.dtos.matricula;

import com.oxeschool.api.enums.StatusCurso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AlunoMatriculadoResponse {

    private Long idAluno;
    private String nome;
    private String email;
    private StatusCurso status;
    private Double progresso;

}
