package com.oxeschool.api.dtos.usuario.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProfessorResponse extends UsuarioResponse {

    private List<Integer> cursosMinistrados; // futuro: Substituído quando a FK for p/ CursoEntity

    public ProfessorResponse(Long id, String nome, String email, List<Integer> cursosMinistrados) {
        super(id, nome, email);
        this.cursosMinistrados = cursosMinistrados;
    }
}
