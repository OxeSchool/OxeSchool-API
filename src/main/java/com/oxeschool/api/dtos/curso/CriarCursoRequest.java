package com.oxeschool.api.dtos.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CriarCursoRequest {

    @NotBlank(message = "nome é obrigatorio")
    private String nome;

    @NotBlank(message = "descrição é obrigatorio")
    private String descricao;

    @NotNull(message = "nome é obrigatorio")
    private Long idProfessor;

    @NotBlank(message = "categoria é obrigatoria")
    private String categoria;

    @NotNull(message = "carga horaria é obrigatorio")
    private Long cargaHoraria;

}
