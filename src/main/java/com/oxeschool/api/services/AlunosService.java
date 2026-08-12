package com.oxeschool.api.services;

import com.oxeschool.api.dtos.usuario.response.AlunoResponse;
import com.oxeschool.api.dtos.usuario.request.RegistrarAlunoRequest;
import com.oxeschool.api.dtos.usuario.response.AuthResponse;
import com.oxeschool.api.entity.AlunoEntity;
import com.oxeschool.api.enums.TiposDeUsuarios;
import com.oxeschool.api.exceptions.customs.aluno.AlunoJaExisteException;
import com.oxeschool.api.exceptions.customs.usuario.UsuarioJaExisteException;
import com.oxeschool.api.jwt.JwtService;
import com.oxeschool.api.mappers.AlunoMapper;
import com.oxeschool.api.repository.UsuariosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AlunosService {

    final private AlunoMapper alunoMapper;
    final private PasswordEncoder passwordEncoder;
    final private UsuariosRepository usuariosRepository;
    final private JwtService jwtService;

    public AlunosService(AlunoMapper alunoMapper,
                         PasswordEncoder passwordEncoder,
                         UsuariosRepository usuariosRepository,
                         JwtService jwtService) {
        this.alunoMapper = alunoMapper;
        this.passwordEncoder = passwordEncoder;
        this.usuariosRepository = usuariosRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse registrar(RegistrarAlunoRequest registrarAlunoRequest){

        var jaExiste = usuariosRepository.existsByEmail(registrarAlunoRequest.getEmail());

        if (jaExiste){
            throw new UsuarioJaExisteException();
        }

        var senhaCriptografada = passwordEncoder.encode(registrarAlunoRequest.getSenha());

        var novoAluno = AlunoEntity.builder()
                .nome(registrarAlunoRequest.getNome())
                .email(registrarAlunoRequest.getEmail())
                .senha(senhaCriptografada)
                .build();

        var alunoSalvo = usuariosRepository.save(novoAluno);

        var tokens = jwtService.criarTokens(alunoSalvo.getId(), TiposDeUsuarios.Aluno.name());

        return new AuthResponse(tokens, alunoMapper.toAlunoResponse(alunoMapper.toAlunoDomain(alunoSalvo)));
    }

}
