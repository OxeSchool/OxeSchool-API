package com.oxeschool.api.exceptions.handlers;

import com.oxeschool.api.exceptions.CustomErrorResponse;
import com.oxeschool.api.exceptions.customs.aluno.AlunoJaExisteException;
import com.oxeschool.api.exceptions.customs.aluno.AlunoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.usuario.UsuarioJaExisteException;
import com.oxeschool.api.exceptions.customs.usuario.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsuarioExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<CustomErrorResponse> usuarioNaoEncontradoExceptionHandler(UsuarioNaoEncontradoException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(exception.getMessage(), 404));
    }

    @ExceptionHandler(UsuarioJaExisteException.class)
    public ResponseEntity<CustomErrorResponse> usuarioJaExisteExceptionHandler(UsuarioJaExisteException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(exception.getMessage(), 409));
    }

}
