package com.oxeschool.api.exceptions.customs.token;

public class TokenExpiradoException extends RuntimeException {

    public TokenExpiradoException() {
        super("Token is expired");
    }

}
