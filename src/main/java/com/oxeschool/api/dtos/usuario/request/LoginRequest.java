package com.oxeschool.api.dtos.usuario.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "email é obrigatorio")
    private String email;

    @NotBlank(message = "senha é obrigatoria")
    private String senha;

}
