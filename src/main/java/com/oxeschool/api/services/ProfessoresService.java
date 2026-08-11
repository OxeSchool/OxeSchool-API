package com.oxeschool.api.services;

import com.oxeschool.api.dtos.usuario.response.ProfessorResponse;
import com.oxeschool.api.dtos.usuario.request.RegistrarProfessorRequest;
import com.oxeschool.api.entity.ProfessorEntity;
import com.oxeschool.api.exceptions.customs.professor.ProfessorJaExisteException;
import com.oxeschool.api.mappers.ProfessorMapper;
import com.oxeschool.api.repository.UsuariosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfessoresService {

    final private PasswordEncoder passwordEncoder;
    final private ProfessorMapper professorMapper;
    final private UsuariosRepository usuariosRepository;

    public  ProfessoresService(PasswordEncoder passwordEncoder,
                               ProfessorMapper professorMapper, UsuariosRepository usuariosRepository) {
        this.passwordEncoder = passwordEncoder;
        this.professorMapper = professorMapper;
        this.usuariosRepository = usuariosRepository;
    }

    public ProfessorResponse registrar(RegistrarProfessorRequest registrarProfessorRequest){

        var jaExiste = usuariosRepository.existsByEmail(registrarProfessorRequest.getEmail());

        if (jaExiste){
            throw new ProfessorJaExisteException();
        }

        var senhaCriptografada = passwordEncoder.encode(registrarProfessorRequest.getSenha());

        var novoProfessor = ProfessorEntity.builder()
                .nome(registrarProfessorRequest.getNome())
                .email(registrarProfessorRequest.getEmail())
                .senha(senhaCriptografada)
                .build();

        var professorSalvo = usuariosRepository.save(novoProfessor);

        return professorMapper.toProfessorResponse(professorMapper.toProfessorDomain(professorSalvo));
    }

}
