package com.oxeschool.api.exceptions.customs.aluno;

public class AlunoNaoEncontradoException extends RuntimeException {
    public AlunoNaoEncontradoException() {
        super("Aluno não encontrado");
    }
}
