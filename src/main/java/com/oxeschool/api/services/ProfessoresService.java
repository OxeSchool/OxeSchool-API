package com.oxeschool.api.services;

import com.oxeschool.api.dtos.professor.ProfessorResponse;
import com.oxeschool.api.dtos.professor.RegistrarProfessorRequest;
import com.oxeschool.api.entity.ProfessorEntity;
import com.oxeschool.api.exceptions.customs.professor.ProfessorJaExisteException;
import com.oxeschool.api.mappers.ProfessorMapper;
import com.oxeschool.api.repository.ProfessoresRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfessoresService {

    final private ProfessoresRepository professoresRepository;
    final private PasswordEncoder passwordEncoder;
    final private ProfessorMapper professorMapper;

    public  ProfessoresService(ProfessoresRepository professoresRepository,
                               PasswordEncoder passwordEncoder,
                               ProfessorMapper professorMapper) {
        this.professoresRepository = professoresRepository;
        this.passwordEncoder = passwordEncoder;
        this.professorMapper = professorMapper;
    }

    public ProfessorResponse registrar(RegistrarProfessorRequest registrarProfessorRequest){

        var jaExiste = professoresRepository.existsByEmail(registrarProfessorRequest.getEmail());

        if (jaExiste){
            throw new ProfessorJaExisteException();
        }

        var senhaCriptografada = passwordEncoder.encode(registrarProfessorRequest.getSenha());

        var novoProfessor = ProfessorEntity.builder()
                .nome(registrarProfessorRequest.getNome())
                .email(registrarProfessorRequest.getEmail())
                .senha(senhaCriptografada)
                .build();

        var professorSalvo = professoresRepository.save(novoProfessor);

        return professorMapper.toProfessorResponse(professorMapper.toProfessorDomain(professorSalvo));
    }

}
