package com.oxeschool.api.services;

import com.oxeschool.api.dtos.usuario.response.AlunoResponse;
import com.oxeschool.api.dtos.usuario.request.RegistrarAlunoRequest;
import com.oxeschool.api.entity.AlunoEntity;
import com.oxeschool.api.exceptions.customs.aluno.AlunoJaExisteException;
import com.oxeschool.api.mappers.AlunoMapper;
import com.oxeschool.api.repository.AlunosRepository;
import com.oxeschool.api.repository.UsuariosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunosService {

    final private AlunosRepository alunosRepository;
    final private AlunoMapper alunoMapper;
    final private PasswordEncoder passwordEncoder;
    final private UsuariosRepository usuariosRepository;

    public AlunosService(AlunosRepository alunosRepository, AlunoMapper alunoMapper, PasswordEncoder passwordEncoder, UsuariosRepository usuariosRepository) {
        this.alunosRepository = alunosRepository;
        this.alunoMapper = alunoMapper;
        this.passwordEncoder = passwordEncoder;
        this.usuariosRepository = usuariosRepository;
    }

    public AlunoResponse registrar(RegistrarAlunoRequest registrarAlunoRequest){

        var jaExiste = usuariosRepository.existsByEmail(registrarAlunoRequest.getEmail());

        if (jaExiste){
            throw new AlunoJaExisteException();
        }

        var senhaCriptografada = passwordEncoder.encode(registrarAlunoRequest.getSenha());

        var novoAluno = AlunoEntity.builder()
                .nome(registrarAlunoRequest.getNome())
                .email(registrarAlunoRequest.getEmail())
                .senha(senhaCriptografada)
                .build();

        var alunoSalvo = alunosRepository.save(novoAluno);

        return alunoMapper.toAlunoResponse(alunoMapper.toAlunoDomain(alunoSalvo));
    }

}
