package com.oxeschool.api.controllers;

import com.oxeschool.api.AlunosService;
import com.oxeschool.api.dtos.AlunoResponse;
import com.oxeschool.api.dtos.RegistrarAlunoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrar")
@RequiredArgsConstructor
public class RegistrosController {

    private AlunosService alunosService;

    @PostMapping("/aluno")
    public ResponseEntity<AlunoResponse> registrarAluno(@RequestBody RegistrarAlunoRequest registrarAlunoRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(alunosService.registrar(registrarAlunoRequest));
    }

}
