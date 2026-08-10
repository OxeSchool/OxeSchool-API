package com.oxeschool.api.dtos.curso;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CriarAulaRequest {

    private String titulo;
    private String texto;
    private String videoUrl;


}
