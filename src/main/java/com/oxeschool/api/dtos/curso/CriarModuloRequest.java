package com.oxeschool.api.dtos.curso;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CriarModuloRequest {

    @NotBlank(message = "nome é obrigatorio")
    private String nome;

}
