package com.oxeschool.api.web;

import com.oxeschool.api.dtos.matricula.CriarMatriculaRequest;
import com.oxeschool.api.entity.Curso.CursoEntity;
import com.oxeschool.api.enums.StatusCurso;
import com.oxeschool.api.exceptions.customs.aluno.AlunoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.curso.CursoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.matricula.MatriculaJaExisteException;
import com.oxeschool.api.repository.AlunosRepository;
import com.oxeschool.api.repository.CursosRepository;
import com.oxeschool.api.repository.MatriculasRepository;
import com.oxeschool.api.repository.ProfessoresRepository;
import com.oxeschool.api.services.MatriculaService;
import com.oxeschool.api.web.forms.MatriculaForm;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/matriculas")
public class MatriculaViewController {

    private final MatriculaService matriculaService;
    private final CursosRepository cursosRepository;
    private final AlunosRepository alunosRepository;
    private final ProfessoresRepository professoresRepository;
    private final MatriculasRepository matriculasRepository;

    public MatriculaViewController(MatriculaService matriculaService,
                                   CursosRepository cursosRepository,
                                   AlunosRepository alunosRepository,
                                   ProfessoresRepository professoresRepository,
                                   MatriculasRepository matriculasRepository) {
        this.matriculaService = matriculaService;
        this.cursosRepository = cursosRepository;
        this.alunosRepository = alunosRepository;
        this.professoresRepository = professoresRepository;
        this.matriculasRepository = matriculasRepository;
    }

    @GetMapping
    public String formulario(Model model) {
        prepararModel(model, new MatriculaForm());
        return "matriculas/matriculas";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("matriculaForm") MatriculaForm form,
                        BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                matriculaService.criarMatricula(
                        new CriarMatriculaRequest(form.getAlunoId(), form.getCursoId()));
                model.addAttribute("sucesso", "Matrícula realizada com sucesso!");
            } catch (AlunoNaoEncontradoException | CursoNaoEncontradoException | MatriculaJaExisteException exception) {
                model.addAttribute("erro", exception.getMessage());
            }
        }

        prepararModel(model, form);
        return "matriculas/matriculas";
    }

    private void prepararModel(Model model, MatriculaForm form) {
        if (!model.containsAttribute("matriculaForm")) {
            model.addAttribute("matriculaForm", form);
        }

        List<CursoEntity> cursos = cursosRepository
                .findAllByStatus(PageRequest.of(0, 50), StatusCurso.ATIVO)
                .getContent();

        model.addAttribute("cursosDisponiveis", cursos);
        model.addAttribute("alunosDisponiveis", alunosRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
        model.addAttribute("matriculas", montarLinhasDeMatricula());
    }

    private List<Map<String, Object>> montarLinhasDeMatricula() {
        return matriculasRepository.findAll(Sort.by(Sort.Direction.DESC, "dataMatricula")).stream()
                .map(matricula -> {
                    Map<String, Object> linha = new HashMap<>();

                    String nomeAluno = matricula.getIdAluno() == null
                            ? "Aluno desconhecido"
                            : alunosRepository.findById(matricula.getIdAluno())
                                    .map(aluno -> aluno.getNome())
                                    .orElse("Aluno " + matricula.getIdAluno());

                    var curso = matricula.getIdCurso() == null
                            ? null
                            : cursosRepository.findById(matricula.getIdCurso()).orElse(null);

                    linha.put("aluno", nomeAluno);
                    linha.put("curso", curso != null ? curso.getNome() : "Curso indisponível");
                    linha.put("professor", nomeDoProfessor(curso));
                    linha.put("data", matricula.getDataMatricula() != null
                            ? matricula.getDataMatricula()
                            : LocalDateTime.now());
                    linha.put("status", matricula.getStatus() != null ? matricula.getStatus() : StatusCurso.INATIVO);
                    return linha;
                })
                .toList();
    }

    private String nomeDoProfessor(CursoEntity curso) {
        if (curso == null || curso.getIdProfessor() == null) {
            return "—";
        }
        return professoresRepository.findById(curso.getIdProfessor())
                .map(professor -> professor.getNome())
                .orElse("—");
    }

}