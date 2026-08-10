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

    public Modulo(UUID id, String nome){
        this.id = id;
        this.nome = nome;
        this.aulas = new ArrayList<>();
    }

    private UUID id;
    private String nome;
    private List<Aulas> aulas;

}
