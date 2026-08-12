package com.oxeschool.api.dtos.curso;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CriarAulaRequest {

    @NotBlank(message = "titulo é obrigatorio")
    private String titulo;

    @NotBlank(message = "texto é obrigatorio")
    private String texto;

    @NotBlank(message = "url do video é obrigatoria")
    private String videoUrl;


}
