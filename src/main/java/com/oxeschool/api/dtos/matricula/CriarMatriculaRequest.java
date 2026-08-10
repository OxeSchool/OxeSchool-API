package com.oxeschool.api.dtos.matricula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CriarMatriculaRequest {

    private Long idAluno;
    private UUID idCurso;

}
