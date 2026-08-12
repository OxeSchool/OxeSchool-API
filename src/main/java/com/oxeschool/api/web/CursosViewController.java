package com.oxeschool.api.web;

import com.oxeschool.api.exceptions.customs.curso.CursoNaoEncontradoException;
import com.oxeschool.api.services.CursosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CursosViewController {

    private final CursosService cursosService;

    public CursosViewController(CursosService cursosService) {
        this.cursosService = cursosService;
    }

    @GetMapping("/cursos")
    public String listar(Model model) {
        var cursos = cursosService.listarDisponiveis().stream()
                .map(curso -> Map.<String, Object>of(
                        "id", curso.getId(),
                        "nome", curso.getNome(),
                        "professor", curso.getProfessorNome() == null ? "" : curso.getProfessorNome(),
                        "categoria", curso.getCategoria(),
                        "cargaHoraria", curso.getCargaHoraria()
                ))
                .toList();

        model.addAttribute("cursos", cursos);
        return "cursos/cursos-lista";
    }

    @GetMapping("/cursos/{id}")
    public String detalhe(@PathVariable UUID id, Model model) {
        try {
            var curso = cursosService.pegarCursoPorId(id);

            model.addAttribute("curso", Map.of(
                    "id", curso.getId(),
                    "nome", curso.getNome(),
                    "descricao", curso.getDescricao(),
                    "professor", curso.getProfessorNome() == null ? "" : curso.getProfessorNome(),
                    "categoria", curso.getCategoria(),
                    "cargaHoraria", curso.getCargaHoraria(),
                    "status", curso.getStatus(),
                    "matriculado", false
            ));
            model.addAttribute("papel", "ALUNO");
            model.addAttribute("aulas", List.of());
            model.addAttribute("alunosDaTurma", List.of());
        } catch (CursoNaoEncontradoException exception) {
            model.addAttribute("naoEncontrado", true);
        }

        return "cursos/curso-detalhe";
    }

}
