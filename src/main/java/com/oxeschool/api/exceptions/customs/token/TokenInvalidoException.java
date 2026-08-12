package com.oxeschool.api.exceptions.customs.token;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException() {
        super("Token is invalid");
    }
}
