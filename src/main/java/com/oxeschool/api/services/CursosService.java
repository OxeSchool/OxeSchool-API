package com.oxeschool.api.services;

import com.oxeschool.api.dtos.curso.CriarCursoRequest;
import com.oxeschool.api.dtos.curso.CursoResponse;
import com.oxeschool.api.entity.CursoEntity;
import com.oxeschool.api.entity.StatusCurso;
import com.oxeschool.api.exceptions.customs.curso.CursoJaExisteException;
import com.oxeschool.api.mappers.CursoMapper;
import com.oxeschool.api.repository.CursosRepository;
import org.springframework.stereotype.Service;

@Service
public class CursosService {

    final private CursosRepository cursosRepository;
    final private CursoMapper cursoMapper;

    public CursosService(CursosRepository cursosRepository, CursoMapper cursoMapper) {
        this.cursosRepository = cursosRepository;
        this.cursoMapper = cursoMapper;
    }

    public CursoResponse criar(CriarCursoRequest criarCursoRequest) {

        var jaExiste = cursosRepository.existsByNomeAndProfessorId(
                criarCursoRequest.getNome(),
                criarCursoRequest.getProfessorId()
        );

        if (jaExiste) {
            throw new CursoJaExisteException();
        }

        var novoCurso = CursoEntity.builder()
                .nome(criarCursoRequest.getNome())
                .descricao(criarCursoRequest.getDescricao())
                .professorId(criarCursoRequest.getProfessorId())
                .categoria(criarCursoRequest.getCategoria())
                .cargaHoraria(criarCursoRequest.getCargaHoraria())
                .status(StatusCurso.ATIVO)
                .build();

        var cursoSalvo = cursosRepository.save(novoCurso);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(cursoSalvo));
    }

}
