package com.oxeschool.api.entity;

import com.oxeschool.api.enums.StatusCurso;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "Matricula-info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MatriculaEntity {

    @Id
    private UUID id;
    private UUID idAluno;
    private UUID idCurso;
    private List<UUID> aulasConcluidas;
    private StatusCurso status;
    private LocalDateTime dataMatricula;

}
