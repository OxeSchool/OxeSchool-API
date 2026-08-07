package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.usuario.response.ProfessorResponse;
import com.oxeschool.api.dtos.usuario.request.RegistrarProfessorRequest;
import com.oxeschool.api.services.AlunosService;
import com.oxeschool.api.dtos.usuario.response.AlunoResponse;
import com.oxeschool.api.dtos.usuario.request.RegistrarAlunoRequest;

import com.oxeschool.api.services.ProfessoresService;
import jakarta.validation.Valid;
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
    private final ProfessoresService professoresService;

    public RegistrosController(AlunosService alunosService,
                               ProfessoresService professoresService) {
        this.alunosService = alunosService;
        this.professoresService = professoresService;
    }

    @PostMapping("/aluno")
    public ResponseEntity<AlunoResponse> registrarAluno(@Valid @RequestBody RegistrarAlunoRequest registrarAlunoRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(alunosService.registrar(registrarAlunoRequest));
    }

    @PostMapping("/professor")
    public ResponseEntity<ProfessorResponse> registrarProfessor(@Valid @RequestBody RegistrarProfessorRequest registrarProfessorRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(professoresService.registrar(registrarProfessorRequest));
    }

}
