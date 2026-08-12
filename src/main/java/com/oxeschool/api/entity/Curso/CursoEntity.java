package com.oxeschool.api.entity.Curso;

import com.oxeschool.api.enums.StatusCurso;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "Cursos-info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CursoEntity {

    @Id
    private UUID id;
    private Long idProfessor;
    private String nome;
    private String descricao;
    private String categoria;
    private Long cargaHoraria;
    private List<Modulo> modulos;
    private StatusCurso status;

}



