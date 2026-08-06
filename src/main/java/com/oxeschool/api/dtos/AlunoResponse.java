package com.oxeschool.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AlunoResponse {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private List<Integer> cursosIds;

}
