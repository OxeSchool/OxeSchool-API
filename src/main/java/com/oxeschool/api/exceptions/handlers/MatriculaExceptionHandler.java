package com.oxeschool.api.exceptions.handlers;

import com.oxeschool.api.exceptions.CustomErrorResponse;
import com.oxeschool.api.exceptions.customs.aluno.AlunoJaExisteException;
import com.oxeschool.api.exceptions.customs.aluno.AlunoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.matricula.MatriculaJaExisteException;
import com.oxeschool.api.exceptions.customs.matricula.MatriculaNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MatriculaExceptionHandler {

    @ExceptionHandler(MatriculaJaExisteException.class)
    public ResponseEntity<CustomErrorResponse> matriculaJaExisteExceptionHandler(MatriculaJaExisteException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(exception.getMessage(), 409));
    }

    @ExceptionHandler(MatriculaNaoEncontradaException.class)
    public ResponseEntity<CustomErrorResponse> matriculaNaoEncontradoExceptionHandler(MatriculaNaoEncontradaException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(exception.getMessage(), 404));
    }

}
