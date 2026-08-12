package com.oxeschool.api.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class PerfilController {

    // TODO: pegar o usuário logado via Spring Security (Authentication / Principal)
    // em vez do mock abaixo, assim que a autenticação estiver pronta.

    @GetMapping("/perfil")
    public String perfil(Model model) {
        model.addAttribute("usuario", Map.of(
                "nome", "Maria Oliveira",
                "email", "maria@oxeschool.com",
                "tipo", "ALUNO" // ou "PROFESSOR" — controla qual bloco a view mostra
        ));

        model.addAttribute("cursosEmProgresso", List.of(
                Map.of("id", 1, "nome", "Java para iniciantes", "progresso", 62, "qtdAlunos", 34)
        ));

        model.addAttribute("cursosConcluidos", List.of(
                Map.of("id", 2, "nome", "Lógica de programação")
        ));

        return "perfil/perfil";
    }
}
