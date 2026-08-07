package com.oxeschool.api.exceptions.customs.curso;

public class CursoJaExisteException extends RuntimeException {
    public CursoJaExisteException() {
        super("Este professor já possui um curso com esse nome");
    }
}
