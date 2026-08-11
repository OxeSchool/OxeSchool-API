package com.oxeschool.api.dtos.usuario.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EditarEmailUsuarioRequest {

    @NotBlank(message = "email é obrigatório")
    @Email(message = "email inválido")
    private String novoEmail;

}
