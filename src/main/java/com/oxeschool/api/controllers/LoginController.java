package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.usuario.request.LoginRequest;
import com.oxeschool.api.dtos.usuario.response.AuthResponse;
import com.oxeschool.api.services.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    final private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/aluno")
    public ResponseEntity<AuthResponse> loginAluno(@Valid @RequestBody LoginRequest loginRequest){

        return ResponseEntity.status(HttpStatus.OK).body(loginService.loginAluno(loginRequest));
    }

    @PostMapping("/professor")
    public ResponseEntity<AuthResponse> loginProfessor(@Valid @RequestBody LoginRequest loginRequest){

        return ResponseEntity.status(HttpStatus.OK).body(loginService.loginProfessor(loginRequest));
    }

}
