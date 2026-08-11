package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.usuario.request.EditarEmailUsuarioRequest;
import com.oxeschool.api.dtos.usuario.request.EditarNomeUsuarioRequest;
import com.oxeschool.api.dtos.usuario.request.EditarSenhaUsuarioRequest;
import com.oxeschool.api.dtos.usuario.response.UsuarioResponse;
import com.oxeschool.api.services.UsuariosService;
import jakarta.servlet.ServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuariosController {

    final private UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService){
        this.usuariosService = usuariosService;
    }

    @PatchMapping("/{id}/nome")
    public ResponseEntity<UsuarioResponse> editarNome(@RequestBody EditarNomeUsuarioRequest editarNomeUsuarioRequest, @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(usuariosService.editarNomeUsuario(editarNomeUsuarioRequest, id));
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<UsuarioResponse> editarEmail(@RequestBody EditarEmailUsuarioRequest editarEmailUsuarioRequest, @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(usuariosService.editarEmailUsuario(editarEmailUsuarioRequest, id));
    }

    @PatchMapping("/{id}/senha")
    public ResponseEntity<UsuarioResponse> editarSenha(@RequestBody EditarSenhaUsuarioRequest editarSenhaUsuarioRequest, @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(usuariosService.editarSenhaUsuario(editarSenhaUsuarioRequest, id));
    }




}
