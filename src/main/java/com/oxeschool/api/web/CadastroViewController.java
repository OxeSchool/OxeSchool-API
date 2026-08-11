package com.oxeschool.api.web;

import com.oxeschool.api.dtos.usuario.request.RegistrarAlunoRequest;
import com.oxeschool.api.dtos.usuario.request.RegistrarProfessorRequest;
import com.oxeschool.api.dtos.usuario.response.UsuarioResponse;
import com.oxeschool.api.exceptions.customs.aluno.AlunoJaExisteException;
import com.oxeschool.api.exceptions.customs.professor.ProfessorJaExisteException;
import com.oxeschool.api.services.AlunosService;
import com.oxeschool.api.services.ProfessoresService;
import com.oxeschool.api.web.forms.CadastroForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastro")
public class CadastroViewController {

    private final AlunosService alunosService;
    private final ProfessoresService professoresService;

    public CadastroViewController(AlunosService alunosService, ProfessoresService professoresService) {
        this.alunosService = alunosService;
        this.professoresService = professoresService;
    }

    @GetMapping
    public String formulario(Model model) {
        if (!model.containsAttribute("cadastroForm")) {
            model.addAttribute("cadastroForm", new CadastroForm());
        }
        return "cadastro/form";
    }

    @PostMapping
    public String registrar(@Valid @ModelAttribute("cadastroForm") CadastroForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "cadastro/form";
        }

        try {
            UsuarioResponse response = form.getTipo() == CadastroForm.Tipo.PROFESSOR
                    ? professoresService.registrar(paraRegistrarProfessorRequest(form))
                    : alunosService.registrar(paraRegistrarAlunoRequest(form));

            model.addAttribute("nome", response.getNome());
            model.addAttribute("email", response.getEmail());
            model.addAttribute("tipo", form.getTipo());
            return "cadastro/sucesso";
        } catch (AlunoJaExisteException | ProfessorJaExisteException exception) {
            model.addAttribute("erro", exception.getMessage());
            return "cadastro/form";
        }
    }

    private RegistrarAlunoRequest paraRegistrarAlunoRequest(CadastroForm form) {
        var request = new RegistrarAlunoRequest();
        request.setNome(form.getNome());
        request.setEmail(form.getEmail());
        request.setSenha(form.getSenha());
        return request;
    }

    private RegistrarProfessorRequest paraRegistrarProfessorRequest(CadastroForm form) {
        var request = new RegistrarProfessorRequest();
        request.setNome(form.getNome());
        request.setEmail(form.getEmail());
        request.setSenha(form.getSenha());
        return request;
    }

}
