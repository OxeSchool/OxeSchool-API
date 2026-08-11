package com.oxeschool.api.web.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroForm {

    public enum Tipo {
        ALUNO,
        PROFESSOR
    }

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @NotBlank(message = "email é obrigatório")
    @Email(message = "email inválido")
    private String email;

    @NotBlank(message = "senha é obrigatória")
    @Size(min = 8, message = "senha deve ter no mínimo 8 caracteres")
    private String senha;

    @NotNull(message = "selecione o tipo de usuário")
    private Tipo tipo = Tipo.ALUNO;

}
