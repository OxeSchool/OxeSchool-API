package com.oxeschool.api.dtos.curso;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CriarAulaRequest {

    private String titulo;
    private String texto;
    private String videoUrl;


}
