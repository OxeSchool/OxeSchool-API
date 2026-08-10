package com.oxeschool.api.exceptions.customs.curso;

public class ModuloNaoEncontradoException extends RuntimeException {
    public ModuloNaoEncontradoException() {
        super("Modulo não encontrado");
    }
}
