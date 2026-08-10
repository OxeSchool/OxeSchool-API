package com.oxeschool.api.exceptions.handlers;

import com.oxeschool.api.exceptions.CustomErrorResponse;
import com.oxeschool.api.exceptions.customs.aluno.AlunoJaExisteException;
import com.oxeschool.api.exceptions.customs.aluno.AlunoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AlunoExceptionHandler {

    @ExceptionHandler(AlunoJaExisteException.class)
    public ResponseEntity<CustomErrorResponse> alunoJaExisteExceptionHandler(AlunoJaExisteException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(exception.getMessage(), 409));
    }

    @ExceptionHandler(AlunoNaoEncontradoException.class)
    public ResponseEntity<CustomErrorResponse> alunoNaoEncontradoExceptionHandler(AlunoNaoEncontradoException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(exception.getMessage(), 404));
    }

}
