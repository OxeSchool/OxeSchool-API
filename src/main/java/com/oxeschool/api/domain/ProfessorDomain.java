package com.oxeschool.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProfessorDomain {

    private Long id;
    private String nome;
    private String email;

}
