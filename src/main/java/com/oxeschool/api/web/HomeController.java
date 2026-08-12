package com.oxeschool.api.web;

import com.oxeschool.api.entity.Curso.CursoEntity;
import com.oxeschool.api.enums.StatusCurso;
import com.oxeschool.api.repository.CursosRepository;
import com.oxeschool.api.repository.ProfessoresRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final CursosRepository cursosRepository;
    private final ProfessoresRepository professoresRepository;

    public HomeController(CursosRepository cursosRepository,
                          ProfessoresRepository professoresRepository) {
        this.cursosRepository = cursosRepository;
        this.professoresRepository = professoresRepository;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<CursoEntity> cursos = cursosRepository
                .findAllByStatus(PageRequest.of(0, 8), StatusCurso.ATIVO)
                .getContent();

        List<Map<String, Object>> cursosDisponiveis = cursos.stream()
                .map(curso -> {
                    Map<String, Object> view = new HashMap<>();
                    view.put("nome", curso.getNome());
                    view.put("categoria", curso.getCategoria());
                    view.put("professor", nomeDoProfessor(curso));
                    return view;
                })
                .toList();

        List<String> categorias = cursos.stream()
                .map(CursoEntity::getCategoria)
                .distinct()
                .toList();

        // TODO: preencher "ultimosCursos" (com progresso) quando houver sessão/rastreio de acesso
        model.addAttribute("ultimosCursos", List.of());
        model.addAttribute("categorias", categorias);
        model.addAttribute("cursosDisponiveis", cursosDisponiveis);

        return "home/home";
    }

    private String nomeDoProfessor(CursoEntity curso) {
        if (curso.getIdProfessor() == null) {
            return "—";
        }
        return professoresRepository.findById(curso.getIdProfessor())
                .map(professor -> professor.getNome())
                .orElse("—");
    }

}