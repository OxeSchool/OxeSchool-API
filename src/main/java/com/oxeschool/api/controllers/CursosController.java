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
    public ResponseEntity<CursoResponse> editarCurso(@RequestHeader("Authorization") String token, @PathVariable UUID cursoId, @RequestBody EditarCursoRequest editarCursoRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(cursosService.editar(token, cursoId, editarCursoRequest));
    }

    @PostMapping("/{cursoId}/modulo")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> criarModulo(@PathVariable UUID cursoId, @RequestBody CriarModuloRequest criarModuloRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.adicionarModulo(cursoId, criarModuloRequest));
    }

    @PutMapping("/{cursoId}/modulo/{moduloId}")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> editarModulo(@RequestHeader("Authorization") String token, @PathVariable UUID cursoId, @PathVariable UUID moduloId, @RequestBody EditarModuloRequest editarModuloRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(cursosService.editarModulo(token, cursoId, moduloId, editarModuloRequest));
    }

    @DeleteMapping("/{cursoId}/modulo/{moduloId}")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> excluirModulo(@RequestHeader("Authorization") String token, @PathVariable UUID cursoId, @PathVariable UUID moduloId) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cursosService.excluirModulo(token, cursoId, moduloId));
    }

    @PostMapping("/{cursoId}/modulo/{moduloId}/aula")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> criarAula(@PathVariable UUID cursoId, @PathVariable UUID moduloId, @RequestBody CriarAulaRequest criarAulaRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.adicionarAula(cursoId, moduloId, criarAulaRequest));
    }

    @PutMapping("/{cursoId}/modulo/{moduloId}/aula/{aulaId}")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> editarAula(@RequestHeader("Authorization") String token, @PathVariable UUID cursoId, @PathVariable UUID moduloId, @PathVariable UUID aulaId, @RequestBody EditarAulaRequest editarAulaRequest){

        return ResponseEntity.status(HttpStatus.OK).body(cursosService.editarAula(token, cursoId, moduloId, aulaId, editarAulaRequest));
    }

    @DeleteMapping("/{cursoId}/modulo/{moduloId}/aula/{aulaId}")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> excluirAula(@RequestHeader("Authorization") String token, @PathVariable UUID cursoId, @PathVariable UUID moduloId, @PathVariable UUID aulaId){

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cursosService.excluirAula(token, cursoId, moduloId, aulaId));
    }

    @PutMapping("/{cursoId}/status")
    @PreAuthorize("hasRole('Professor')")
    public ResponseEntity<CursoResponse> editarStatusCurso(@RequestHeader("Authorization") String token, @PathVariable UUID cursoId, @RequestBody EditarStatusCursoRequest editarStatusCursoRequest){

        return ResponseEntity.status(HttpStatus.OK).body(cursosService.editarStatus(token, cursoId, editarStatusCursoRequest));
    }

}
