package com.oxeschool.api.web;

import com.oxeschool.api.enums.StatusCurso;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class CursosViewController {

    // TODO: injetar o CursosService real via construtor assim que o backend tiver um endpoint
    // de listagem com descrição, categoria, carga horária e status (RN02 do #28).
    private static final List<Map<String, Object>> CURSOS = List.of(
            Map.of(
                    "id", 1L,
                    "nome", "Lógica de Programação",
                    "descricao", "Fundamentos de lógica e algoritmos para quem está começando a programar.",
                    "professor", "Prof. Carla Menezes",
                    "categoria", "Programação",
                    "cargaHoraria", 40,
                    "status", StatusCurso.ATIVO,
                    "matriculado", true
            ),
            Map.of(
                    "id", 2L,
                    "nome", "Java para Web",
                    "descricao", "Desenvolvimento de APIs REST com Spring Boot, JPA e boas práticas de arquitetura.",
                    "professor", "Prof. Ricardo Alves",
                    "categoria", "Backend",
                    "cargaHoraria", 60,
                    "status", StatusCurso.ATIVO,
                    "matriculado", false
            ),
            Map.of(
                    "id", 3L,
                    "nome", "Front-end com Bootstrap",
                    "descricao", "Construção de interfaces responsivas usando HTML, CSS e o framework Bootstrap.",
                    "professor", "Prof. Fernanda Lima",
                    "categoria", "Front-end",
                    "cargaHoraria", 30,
                    "status", StatusCurso.ATIVO,
                    "matriculado", false
            ),
            Map.of(
                    "id", 4L,
                    "nome", "Banco de Dados Avançado",
                    "descricao", "Modelagem, indexação e otimização de consultas em bancos relacionais e não relacionais.",
                    "professor", "Prof. Bruno Costa",
                    "categoria", "Banco de Dados",
                    "cargaHoraria", 50,
                    "status", StatusCurso.INATIVO,
                    "matriculado", false
            )
    );

    @GetMapping("/cursos")
    public String listar(Model model) {
        var disponiveis = CURSOS.stream()
                .filter(curso -> curso.get("status") == StatusCurso.ATIVO)
                .toList();

        model.addAttribute("cursos", disponiveis);
        return "cursos/cursos-lista";
    }

    @GetMapping("/cursos/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        var curso = CURSOS.stream()
                .filter(c -> c.get("id").equals(id))
                .findFirst();

        if (curso.isEmpty()) {
            model.addAttribute("naoEncontrado", true);
            return "cursos/curso-detalhe";
        }

        model.addAttribute("curso", curso.get());
        model.addAttribute("papel", "ALUNO");
        model.addAttribute("aulas", List.of(
                Map.of("id", 1L, "titulo", "Introdução", "duracao", "12min", "concluida", true),
                Map.of("id", 2L, "titulo", "Variáveis e tipos", "duracao", "18min", "concluida", false)
        ));
        model.addAttribute("alunosDaTurma", List.of());

        return "cursos/curso-detalhe";
    }

}
