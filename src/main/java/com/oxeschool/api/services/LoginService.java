package com.oxeschool.api.services;

import com.oxeschool.api.dtos.usuario.request.LoginRequest;
import com.oxeschool.api.dtos.usuario.response.AuthResponse;
import com.oxeschool.api.enums.TiposDeUsuarios;
import com.oxeschool.api.exceptions.customs.aluno.AlunoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.professor.ProfessorNaoEncontradoException;
import com.oxeschool.api.jwt.JwtService;
import com.oxeschool.api.mappers.UsuarioMapper;
import com.oxeschool.api.repository.AlunosRepository;
import com.oxeschool.api.repository.ProfessoresRepository;
import com.oxeschool.api.repository.UsuariosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AlunosRepository alunosRepository;
    private final ProfessoresRepository professorRepository;
    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UsuarioMapper usuarioMapper;

    public LoginService(AlunosRepository alunosRepository,
                        ProfessoresRepository professorRepository,
                        UsuariosRepository usuariosRepository, PasswordEncoder passwordEncoder,
                        JwtService jwtService,
                        UsuarioMapper usuarioMapper) {
        this.alunosRepository = alunosRepository;
        this.professorRepository = professorRepository;
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.usuarioMapper = usuarioMapper;
    }

    public AuthResponse loginAluno(LoginRequest loginRequest){

        var usuario = usuariosRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(AlunoNaoEncontradoException::new);

        var senhaCorreta = passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha());

        if (!senhaCorreta){
            throw new AlunoNaoEncontradoException();
        }

        var ehAluno = alunosRepository.existsById(usuario.getId());

        if (!ehAluno){
            throw new AlunoNaoEncontradoException();
        }

        var tokens = jwtService.criarTokens(usuario.getId(), TiposDeUsuarios.Aluno.name());

        return new AuthResponse(tokens, usuarioMapper.toUsuarioResponse(usuarioMapper.toUsuarioDomain(usuario)));

    }

    public AuthResponse loginProfessor(LoginRequest loginRequest){

        var usuario = usuariosRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(ProfessorNaoEncontradoException::new);

        var senhaCorreta = passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha());

        if (!senhaCorreta){
            throw new AlunoNaoEncontradoException();
        }

        var ehProfessor = professorRepository.existsById(usuario.getId());

        if (!ehProfessor){
            throw new ProfessorNaoEncontradoException();
        }

        var tokens = jwtService.criarTokens(usuario.getId(), TiposDeUsuarios.Professor.name());

        return new AuthResponse(tokens, usuarioMapper.toUsuarioResponse(usuarioMapper.toUsuarioDomain(usuario)));

    }

}
