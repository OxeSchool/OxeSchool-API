package com.oxeschool.api.dtos.usuario.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AlunoResponse extends UsuarioResponse {

    private List<Integer> cursosMatriculados; // futuro: vira List<Long> refs a matrículas

    public AlunoResponse(Long id, String nome, String email, List<Integer> cursosMatriculados) {
        super(id, nome, email);
        this.cursosMatriculados = cursosMatriculados;
    }
}
