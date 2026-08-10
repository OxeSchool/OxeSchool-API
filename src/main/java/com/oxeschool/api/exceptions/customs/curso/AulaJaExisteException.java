package com.oxeschool.api.exceptions.customs.curso;

public class AulaJaExisteException extends RuntimeException {
    public AulaJaExisteException() {
        super("Já existe uma aula com essas informações nesse modulo");
    }
}
