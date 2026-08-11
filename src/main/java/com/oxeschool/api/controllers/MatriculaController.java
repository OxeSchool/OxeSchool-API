package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.matricula.CursoMatriculadoResponse;
import com.oxeschool.api.dtos.matricula.MatriculaResponse;
import com.oxeschool.api.dtos.matricula.CriarMatriculaRequest;
import com.oxeschool.api.services.MatriculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

    final private MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    public ResponseEntity<MatriculaResponse> criarMatricula(@RequestBody CriarMatriculaRequest criarMatriculaRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(matriculaService.criarMatricula(criarMatriculaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarMatricula(@PathVariable UUID id){

        matriculaService.cancelarMatricula(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Issue #3
    @GetMapping("/aluno/{idAluno}")
    public ResponseEntity<List<CursoMatriculadoResponse>> listarCursosMatriculados(@PathVariable Long idAluno){

        return ResponseEntity.ok(matriculaService.listarCursosMatriculados(idAluno));
    }

}
