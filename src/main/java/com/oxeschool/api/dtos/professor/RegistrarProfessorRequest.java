package com.oxeschool.api.dtos.professor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegistrarProfessorRequest {

    private String nome;
    private String email;
    private String senha;

}
