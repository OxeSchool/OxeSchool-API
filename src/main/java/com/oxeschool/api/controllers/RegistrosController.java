package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.professor.ProfessorResponse;
import com.oxeschool.api.dtos.professor.RegistrarProfessorRequest;
import com.oxeschool.api.services.AlunosService;
import com.oxeschool.api.dtos.aluno.AlunoResponse;
import com.oxeschool.api.dtos.aluno.RegistrarAlunoRequest;

import com.oxeschool.api.services.ProfessoresService;
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
    public ResponseEntity<AlunoResponse> registrarAluno(@RequestBody RegistrarAlunoRequest registrarAlunoRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(alunosService.registrar(registrarAlunoRequest));
    }

    @PostMapping("/professor")
    public ResponseEntity<ProfessorResponse> registrarProfessor(@RequestBody RegistrarProfessorRequest registrarProfessorRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(professoresService.registrar(registrarProfessorRequest));
    }

}
