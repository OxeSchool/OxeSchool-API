package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.curso.*;
import com.oxeschool.api.services.CursosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/curso")
public class CursosController {

    private final CursosService cursosService;

    public CursosController(CursosService cursosService) {
        this.cursosService = cursosService;
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

    @PutMapping("/{cursoId}")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> editarCurso(@PathVariable UUID cursoId, @RequestBody EditarCursoRequest editarCursoRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(cursosService.editar(cursoId, editarCursoRequest));
    }

    @PostMapping("/{cursoId}/modulo")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> criarModulo(@PathVariable UUID cursoId, @RequestBody CriarModuloRequest criarModuloRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.adicionarModulo(cursoId, criarModuloRequest));
    }

    @PutMapping("/{cursoId}/modulo/{moduloId}")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> editarModulo(@PathVariable UUID cursoId, @PathVariable UUID moduloId, @RequestBody EditarModuloRequest editarModuloRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(cursosService.editarModulo(cursoId, moduloId, editarModuloRequest));
    }

    @PostMapping("/{cursoId}/modulo/{moduloId}/aula")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> criarAula(@PathVariable UUID cursoId, @PathVariable UUID moduloId, @RequestBody CriarAulaRequest criarAulaRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.adicionarAula(cursoId, moduloId, criarAulaRequest));
    }

    @PutMapping("/{cursoId}/modulo/{moduloId}/aula/{aulaId}")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> editarAula(@PathVariable UUID cursoId, @PathVariable UUID moduloId, @PathVariable UUID aulaId, @RequestBody EditarAulaRequest editarAulaRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.editarAula(cursoId, moduloId, aulaId, editarAulaRequest));
    }


}
