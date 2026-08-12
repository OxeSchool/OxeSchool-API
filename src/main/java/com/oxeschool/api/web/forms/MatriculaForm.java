package com.oxeschool.api.web.forms;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MatriculaForm {

    @NotNull(message = "selecione um aluno")
    private Long alunoId;

    @NotNull(message = "selecione um curso")
    private UUID cursoId;

}