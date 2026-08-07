package com.oxeschool.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AlunoDomain {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private List<Integer> cursosMatriculados;

}
