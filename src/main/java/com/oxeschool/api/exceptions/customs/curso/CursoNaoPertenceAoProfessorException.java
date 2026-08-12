package com.oxeschool.api.exceptions.customs.curso;

public class CursoNaoPertenceAoProfessorException extends RuntimeException {
    public CursoNaoPertenceAoProfessorException() {
        super("Esse curso não pertence a esse professor");
    }
}
