package com.oxeschool.api.dtos.professor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProfessorResponse {

    private Long id;
    private String nome;
    private String email;

}
