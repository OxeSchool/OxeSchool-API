package com.oxeschool.api.dtos.professor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProfessorResponse {

    private Long id;
    private String nome;
    private String email;
    private List<Integer> cursosMinistrados;

}
