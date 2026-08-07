package com.oxeschool.api.controllers;

import com.oxeschool.api.services.AlunosService;
import com.oxeschool.api.dtos.aluno.AlunoResponse;
import com.oxeschool.api.dtos.aluno.RegistrarAlunoRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrar")
public class RegistrosController {

    private final AlunosService alunosService;

    public RegistrosController(AlunosService alunosService) {
        this.alunosService = alunosService;
    }

    @PostMapping("/aluno")
    public ResponseEntity<AlunoResponse> registrarAluno(@RequestBody RegistrarAlunoRequest registrarAlunoRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(alunosService.registrar(registrarAlunoRequest));
    }

}
