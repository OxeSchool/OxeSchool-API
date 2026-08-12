package com.oxeschool.api.exceptions.handlers;

import com.oxeschool.api.exceptions.CustomErrorResponse;
import com.oxeschool.api.exceptions.customs.curso.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CursoExceptionHandler {

    @ExceptionHandler(CursoJaExisteException.class)
    public ResponseEntity<CustomErrorResponse> cursoJaExisteExceptionHandler(CursoJaExisteException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(exception.getMessage(), 409));
    }

    @ExceptionHandler(ModuloJaExisteException.class)
    public ResponseEntity<CustomErrorResponse> moduloJaExisteExceptionHandler(ModuloJaExisteException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(exception.getMessage(), 409));
    }

    @ExceptionHandler(AulaJaExisteException.class)
    public ResponseEntity<CustomErrorResponse> aulaJaExisteExceptionHandler(AulaJaExisteException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(exception.getMessage(), 409));
    }

    @ExceptionHandler(CursoNaoEncontradoException.class)
    public ResponseEntity<CustomErrorResponse> cursoNaoEncontradoExceptionHandler(CursoNaoEncontradoException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(exception.getMessage(), 404));
    }

    @ExceptionHandler(CursoNaoPertenceAoProfessorException.class)
    public ResponseEntity<CustomErrorResponse> cursoNaoPertenceAoProfessorExceptionHandler(CursoNaoPertenceAoProfessorException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CustomErrorResponse(exception.getMessage(), 403));
    }

    @ExceptionHandler(ModuloNaoEncontradoException.class)
    public ResponseEntity<CustomErrorResponse> moduloNaoEncontradoExceptionHandler(ModuloNaoEncontradoException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(exception.getMessage(), 404));
    }

}
