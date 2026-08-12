package com.oxeschool.api.exceptions.customs.curso;

public class CursoNaoEncontradoException extends RuntimeException {
    public CursoNaoEncontradoException() {
        super("Curso não encontrado");
    }
}
