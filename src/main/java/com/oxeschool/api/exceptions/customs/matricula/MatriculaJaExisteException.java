package com.oxeschool.api.exceptions.customs.matricula;

public class MatriculaJaExisteException extends RuntimeException {
    public MatriculaJaExisteException() {
        super("Já existe uma matricula com esse aluno e curso");
    }
}
