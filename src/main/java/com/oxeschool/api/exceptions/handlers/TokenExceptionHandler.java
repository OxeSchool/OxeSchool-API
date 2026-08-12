package com.oxeschool.api.exceptions.handlers;

import com.oxeschool.api.exceptions.CustomErrorResponse;
import com.oxeschool.api.exceptions.customs.token.TokenExpiradoException;
import com.oxeschool.api.exceptions.customs.token.TokenInvalidoException;
import com.oxeschool.api.exceptions.customs.token.TokenNaBlackListException;
import com.oxeschool.api.exceptions.customs.token.TokenTipoInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler(TokenExpiradoException.class)
    public ResponseEntity<CustomErrorResponse> handlerTokenExpiredException(TokenExpiradoException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomErrorResponse(exception.getMessage(),401));
    }

    @ExceptionHandler(TokenNaBlackListException.class)
    public ResponseEntity<CustomErrorResponse> handlerTokenInBlackListException(TokenNaBlackListException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomErrorResponse(exception.getMessage(),401));
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<CustomErrorResponse> handlerTokenInvalidException(TokenInvalidoException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomErrorResponse(exception.getMessage(),401));
    }

    @ExceptionHandler(TokenTipoInvalidoException.class)
    public ResponseEntity<CustomErrorResponse> handlerTokenInvalidTypeException(TokenTipoInvalidoException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomErrorResponse(exception.getMessage(),401));
    }

}
