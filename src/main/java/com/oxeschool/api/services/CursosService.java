package com.oxeschool.api.services;

import com.oxeschool.api.dtos.curso.CriarCursoRequest;
import com.oxeschool.api.dtos.curso.CursoResponse;
import com.oxeschool.api.entity.Curso.CursoEntity;
import com.oxeschool.api.entity.StatusCurso;
import com.oxeschool.api.exceptions.customs.curso.CursoJaExisteException;
import com.oxeschool.api.exceptions.customs.curso.CursoNaoEncontradoException;
import com.oxeschool.api.mappers.CursoMapper;
import com.oxeschool.api.repository.CursosRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CursosService {

    final private CursosRepository cursosRepository;
    final private CursoMapper cursoMapper;

    public CursosService(CursosRepository cursosRepository, CursoMapper cursoMapper) {
        this.cursosRepository = cursosRepository;
        this.cursoMapper = cursoMapper;
    }

    public CursoResponse criar(CriarCursoRequest criarCursoRequest) {

        var jaExiste = cursosRepository.existsByNomeAndIdProfessor(
                criarCursoRequest.getNome(),
                criarCursoRequest.getIdProfessor()
        );

        if (jaExiste) {
            throw new CursoJaExisteException();
        }

        var novoCurso = CursoEntity.builder()
                .id(UUID.randomUUID())
                .nome(criarCursoRequest.getNome())
                .idProfessor(criarCursoRequest.getIdProfessor())
                .build();

        var cursoSalvo = cursosRepository.save(novoCurso);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(cursoSalvo));
    }

    public CursoResponse pegarCursoPorId(UUID id){

        var curso = cursosRepository.findById(id)
                .orElseThrow(CursoNaoEncontradoException::new);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(curso));

    }

}
