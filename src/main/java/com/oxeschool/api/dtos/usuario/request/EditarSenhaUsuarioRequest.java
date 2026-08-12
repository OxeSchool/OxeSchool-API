package com.oxeschool.api.dtos.usuario.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EditarSenhaUsuarioRequest {

    @NotBlank(message = "nova senha é obrigatória")
    @Size(min = 8, message = "senha deve ter no mínimo 8 caracteres")
    private String novaSenha;

    @NotBlank(message = "senha atual é obrigatória")
    @Size(min = 8, message = "senha deve ter no mínimo 8 caracteres")
    private String senhaAtual;

}
