package com.oxeschool.api.exceptions.customs.professor;

public class ProfessorJaExisteException extends RuntimeException {
    public ProfessorJaExisteException() {
        super("Já existe um professor com esse email");
    }
}
