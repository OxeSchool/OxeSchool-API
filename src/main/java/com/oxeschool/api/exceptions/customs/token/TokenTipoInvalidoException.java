package com.oxeschool.api.exceptions.customs.token;

public class TokenTipoInvalidoException extends RuntimeException {
    public TokenTipoInvalidoException(String message) {
        super("The type of this token is invalid, is required: " + message );
    }
}
