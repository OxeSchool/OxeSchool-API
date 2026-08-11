package com.oxeschool.api.exceptions.customs.token;

public class TokenNaBlackListException extends RuntimeException {
    public TokenNaBlackListException() {
        super("The token is blacklisted.");
    }
}
