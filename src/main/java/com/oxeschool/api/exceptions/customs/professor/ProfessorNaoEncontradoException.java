package com.oxeschool.api.exceptions.customs.professor;

public class ProfessorNaoEncontradoException extends RuntimeException {
    public ProfessorNaoEncontradoException() {
        super("o professor não foi encontrado");
    }
}
