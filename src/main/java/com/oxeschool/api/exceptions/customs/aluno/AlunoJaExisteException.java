package com.oxeschool.api.exceptions.customs.aluno;

public class AlunoJaExisteException extends RuntimeException {
    public AlunoJaExisteException() {
        super("O aluno com esse email já existe");
    }
}
