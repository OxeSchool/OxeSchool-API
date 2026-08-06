package com.oxeschool.api.services;

import com.oxeschool.api.dtos.AlunoResponse;
import com.oxeschool.api.dtos.RegistrarAlunoRequest;
import com.oxeschool.api.entity.AlunoEntity;
import com.oxeschool.api.exceptions.customs.aluno.AlunoJaExisteException;
import com.oxeschool.api.mappers.AlunoMapper;
import com.oxeschool.api.repository.AlunosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunosService {

    final private AlunosRepository alunosRepository;
    final private AlunoMapper alunoMapper;
    final private PasswordEncoder passwordEncoder;

    public AlunosService(AlunosRepository alunosRepository, AlunoMapper alunoMapper, PasswordEncoder passwordEncoder) {
        this.alunosRepository = alunosRepository;
        this.alunoMapper = alunoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public AlunoResponse registrar(RegistrarAlunoRequest registrarAlunoRequest){

        var jaExiste = alunosRepository.existsByEmail(registrarAlunoRequest.getEmail());

        if (jaExiste){
            throw new AlunoJaExisteException();
        }

        var senhaCriptografada = passwordEncoder.encode(registrarAlunoRequest.getSenha());

        var novoAluno = AlunoEntity.builder()
                .nome(registrarAlunoRequest.getNome())
                .email(registrarAlunoRequest.getEmail())
                .senha(senhaCriptografada)
                .cursosIds(List.of())
                .build();

        var alunoSalvo = alunosRepository.save(novoAluno);

        return alunoMapper.toAlunoResponse(alunoMapper.toAlunoDomain(alunoSalvo));
    }

}
