package com.oxeschool.api.dtos.aluno;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegistrarAlunoRequest {

    private String nome;
    private String email;
    private String senha;

}
