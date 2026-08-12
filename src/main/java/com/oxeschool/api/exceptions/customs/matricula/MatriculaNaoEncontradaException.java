package com.oxeschool.api.exceptions.customs.matricula;

public class MatriculaNaoEncontradaException extends RuntimeException {
    public MatriculaNaoEncontradaException() {
        super("Matricula não encontrada");
    }
}
