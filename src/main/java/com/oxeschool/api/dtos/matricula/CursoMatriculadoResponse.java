package com.oxeschool.api.dtos.matricula;

import com.oxeschool.api.enums.StatusCurso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CursoMatriculadoResponse {

    private UUID idCurso;
    private String nomeCurso;
    private StatusCurso status;
    private LocalDateTime dataMatricula;

}
