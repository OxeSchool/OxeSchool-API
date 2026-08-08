package com.oxeschool.api.exceptions.handlers;

import com.oxeschool.api.exceptions.CustomErrorResponse;
import com.oxeschool.api.exceptions.customs.professor.ProfessorJaExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProfessorExceptionHandler {

    @ExceptionHandler(ProfessorJaExisteException.class)
    public ResponseEntity<CustomErrorResponse> professorJaExisteExceptionHandler(ProfessorJaExisteException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(exception.getMessage(), 409));
    }

}
