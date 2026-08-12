package com.oxeschool.api.dtos.usuario.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarUsuarioRequest {

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @NotBlank(message = "email é obrigatório")
    @Email(message = "email inválido")
    private String email;

    @NotBlank(message = "senha é obrigatória")
    @Size(min = 8, message = "senha deve ter no mínimo 8 caracteres")
    private String senha;

}