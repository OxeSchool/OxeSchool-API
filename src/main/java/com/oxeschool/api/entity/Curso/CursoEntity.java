package com.oxeschool.api.entity.Curso;

import lombok.*;
import org.bson.types.ObjectId;
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
    private String nome;
    private Long idProfessor;
    private List<Modulos> modulos;

}



