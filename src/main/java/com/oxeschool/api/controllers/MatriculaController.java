package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.matricula.MatriculaResponse;
import com.oxeschool.api.dtos.matricula.CriarMatriculaRequest;
import com.oxeschool.api.services.MatriculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

    final private MatriculaService matriculaService;

    public  MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    public ResponseEntity<MatriculaResponse> criarMatricula(@RequestBody CriarMatriculaRequest criarMatriculaRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(matriculaService.criarMatricula(criarMatriculaRequest));
    }

}
