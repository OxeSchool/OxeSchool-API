package com.oxeschool.api.exceptions.customs.usuario;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException() {
        super("Usuario não encontrado");
    }
}
