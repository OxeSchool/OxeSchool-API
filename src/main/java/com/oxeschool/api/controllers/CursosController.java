package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.curso.CriarAulaRequest;
import com.oxeschool.api.dtos.curso.CriarCursoRequest;
import com.oxeschool.api.dtos.curso.CriarModuloRequest;
import com.oxeschool.api.dtos.curso.CursoResponse;
import com.oxeschool.api.services.CursosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/curso")
public class CursosController {

    private final CursosService cursosService;

    public CursosController(CursosService cursosService) {
        this.cursosService = cursosService;
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> listarCursosDisponiveis(){

        return ResponseEntity.ok(cursosService.listarDisponiveis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> pegarCursoPorId(@PathVariable UUID id){

        return  ResponseEntity.ok(cursosService.pegarCursoPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> criarCurso(@RequestBody CriarCursoRequest criarCursoRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.criar(criarCursoRequest));
    }

    @PostMapping("/{cursoId}/modulo")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> criarModulo(@PathVariable UUID cursoId, @RequestBody CriarModuloRequest criarModuloRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.adicionarModulo(cursoId, criarModuloRequest));
    }


    @PostMapping("/{cursoId}/modulo/{moduloId}/aula")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> criarAula(@PathVariable UUID cursoId, @PathVariable UUID moduloId, @RequestBody CriarAulaRequest criarAulaRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.adicionarAula(cursoId, moduloId, criarAulaRequest));
    }

}
