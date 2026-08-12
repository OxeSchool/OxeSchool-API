package com.oxeschool.api.dtos.matricula;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CriarMatriculaRequest {

    @NotNull(message = "id do aluno é obrigatorio")
    private Long idAluno;

    @NotNull(message = "id do curso é obrigatorio")
    private UUID idCurso;

}
