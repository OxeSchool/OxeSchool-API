package com.oxeschool.api.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    // TODO: injetar o CursoService real via construtor (@RequiredArgsConstructor do Lombok, por ex.)

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("ultimosCursos", List.of(
                Map.of("nome", "Java para iniciantes", "categoria", "Programação", "progresso", 62),
                Map.of("nome", "Inglês instrumental", "categoria", "Idiomas", "progresso", 30)
        ));

        model.addAttribute("categorias", List.of("Programação", "Idiomas", "Design", "Matemática"));

        model.addAttribute("cursosDisponiveis", List.of(
                Map.of("nome", "Spring Boot na prática", "categoria", "Programação", "professor", "Prof. Ana Souza"),
                Map.of("nome", "Introdução ao Design", "categoria", "Design", "professor", "Prof. Caio Lima"),
                Map.of("nome", "Matemática básica", "categoria", "Matemática", "professor", "Prof. Duda Reis")
        ));

        return "home/home"; // resolve para templates/home/home.html
    }
}