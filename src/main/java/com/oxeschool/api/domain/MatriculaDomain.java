package com.oxeschool.api.domain;

import com.oxeschool.api.enums.StatusCurso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class MatriculaDomain {

    private UUID id;
    private Long idAluno;
    private UUID idCurso;
    private List<UUID> aulasConcluidas;
    private StatusCurso status;
    private LocalDateTime dataMatricula;

}
