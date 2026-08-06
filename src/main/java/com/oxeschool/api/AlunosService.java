package com.oxeschool.api;

import com.oxeschool.api.dtos.AlunoResponse;
import com.oxeschool.api.dtos.RegistrarAlunoRequest;
import com.oxeschool.api.entity.AlunoEntity;
import com.oxeschool.api.exceptions.customs.aluno.AlunoJaExisteException;
import com.oxeschool.api.mappers.AlunoMapper;
import com.oxeschool.api.repository.AlunosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunosService {

    private AlunosRepository alunosRepository;
    private AlunoMapper alunoMapper;
    private PasswordEncoder passwordEncoder;

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
                .build();

        var alunoSalvo = alunosRepository.save(novoAluno);

        return alunoMapper.toAlunoResponse(alunoMapper.toAlunoDomain(alunoSalvo));
    }

}
