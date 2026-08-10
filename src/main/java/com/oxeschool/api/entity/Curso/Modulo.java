package com.oxeschool.api.entity.Curso;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Modulo{

    private UUID id;
    private String nome;
    private List<Aula> aulas;

}
