package com.oxeschool.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProfessorDomain {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private List<Integer> cursosMinistrados;

}
