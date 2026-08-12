package com.oxeschool.api.exceptions.customs.usuario;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException() {
        super("Já existe um usuario com esse email");
    }
}
