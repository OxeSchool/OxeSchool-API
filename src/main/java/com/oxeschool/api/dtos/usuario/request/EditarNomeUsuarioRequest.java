package com.oxeschool.api.dtos.usuario.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EditarNomeUsuarioRequest {

    @NotBlank(message = "nome é obrigatório")
    private String novoNome;

}
