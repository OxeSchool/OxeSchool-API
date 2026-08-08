package com.oxeschool.api.exceptions.customs.aluno;

public class AlunoJaExisteException extends RuntimeException {
    public AlunoJaExisteException() {
        super("Já existe um aluno com esse email");
    }
}
