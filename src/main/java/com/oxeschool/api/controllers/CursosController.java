package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.curso.CriarCursoRequest;
import com.oxeschool.api.dtos.curso.CursoResponse;
import com.oxeschool.api.services.CursosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CursoResponse> criarCurso(@RequestBody CriarCursoRequest criarCursoRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.criar(criarCursoRequest));
    }

}
