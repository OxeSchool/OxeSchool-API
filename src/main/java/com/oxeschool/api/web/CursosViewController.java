package com.oxeschool.api.web;

import com.oxeschool.api.entity.Curso.CursoEntity;
import com.oxeschool.api.enums.StatusCurso;
import com.oxeschool.api.repository.CursosRepository;
import com.oxeschool.api.repository.ProfessoresRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CursosViewController {

    private final CursosRepository cursosRepository;
    private final ProfessoresRepository professoresRepository;

    public CursosViewController(CursosRepository cursosRepository,
                                ProfessoresRepository professoresRepository) {
        this.cursosRepository = cursosRepository;
        this.professoresRepository = professoresRepository;
    }

    @GetMapping("/cursos")
    public String listar(Model model) {
        List<Map<String, Object>> cursos = cursosRepository
                .findAllByStatus(PageRequest.of(0, 100), StatusCurso.ATIVO)
                .getContent()
                .stream()
                .map(this::paraView)
                .toList();

        model.addAttribute("cursos", cursos);
        return "cursos/cursos-lista";
    }

    @GetMapping("/cursos/{id}")
    public String detalhe(@PathVariable UUID id, Model model) {
        var curso = cursosRepository.findById(id).orElse(null);

        if (curso == null) {
            model.addAttribute("naoEncontrado", true);
            return "cursos/curso-detalhe";
        }

        model.addAttribute("curso", paraView(curso));
        model.addAttribute("papel", "ALUNO");
        model.addAttribute("aulas", montarAulas(curso));
        model.addAttribute("alunosDaTurma", List.of());

        return "cursos/curso-detalhe";
    }

    private Map<String, Object> paraView(CursoEntity curso) {
        var view = new HashMap<String, Object>();
        view.put("id", curso.getId());
        view.put("nome", curso.getNome());
        view.put("descricao", curso.getDescricao());
        view.put("categoria", curso.getCategoria());
        view.put("cargaHoraria", curso.getCargaHoraria());
        view.put("status", curso.getStatus());
        view.put("matriculado", false);

        // TODO: marcar "matriculado" quando a sessão informar o aluno logado
        view.put("professor", nomeDoProfessor(curso));
        return view;
    }

    private String nomeDoProfessor(CursoEntity curso) {
        if (curso.getIdProfessor() == null) {
            return "—";
        }
        return professoresRepository.findById(curso.getIdProfessor())
                .map(professor -> professor.getNome())
                .orElse("—");
    }

    private List<Map<String, Object>> montarAulas(CursoEntity curso) {
        if (curso.getModulos() == null) {
            return List.of();
        }

        var aulas = new ArrayList<Map<String, Object>>();
        curso.getModulos().forEach(modulo -> {
            if (modulo.getAulas() == null) {
                return;
            }
            modulo.getAulas().forEach(aula -> {
                var view = new HashMap<String, Object>();
                view.put("id", aula.getId());
                view.put("titulo", aula.getTitulo());
                view.put("concluida", false);
                aulas.add(view);
            });
        });
        return aulas;
    }

}