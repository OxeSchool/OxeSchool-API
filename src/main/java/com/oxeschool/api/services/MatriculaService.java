package com.oxeschool.api.services;

import com.oxeschool.api.dtos.matricula.CursoMatriculadoResponse;
import com.oxeschool.api.dtos.matricula.MatriculaResponse;
import com.oxeschool.api.dtos.matricula.CriarMatriculaRequest;
import com.oxeschool.api.entity.MatriculaEntity;
import com.oxeschool.api.enums.StatusCurso;
import com.oxeschool.api.exceptions.customs.aluno.AlunoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.curso.CursoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.matricula.MatriculaJaExisteException;
import com.oxeschool.api.exceptions.customs.matricula.MatriculaNaoEncontradaException;
import com.oxeschool.api.mappers.MatriculaMapper;
import com.oxeschool.api.repository.AlunosRepository;
import com.oxeschool.api.repository.CursosRepository;
import com.oxeschool.api.repository.MatriculasRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MatriculaService {

    final private MatriculasRepository matriculasRepository;
    final private MatriculaMapper matriculaMapper;
    final private AlunosRepository alunosRepository;
    final private CursosRepository cursosRepository;

    public MatriculaService(
            MatriculasRepository matriculasRepository,
            AlunosRepository alunosRepository,
            CursosRepository cursosRepository,
            MatriculaMapper matriculaMapper) {
        this.matriculasRepository = matriculasRepository;
        this.alunosRepository = alunosRepository;
        this.cursosRepository = cursosRepository;
        this.matriculaMapper = matriculaMapper;
    }

    public MatriculaResponse criarMatricula(CriarMatriculaRequest criarMatriculaRequest) {

        var alunoExiste = alunosRepository.existsById(criarMatriculaRequest.getIdAluno());

        if (!alunoExiste){
            throw new AlunoNaoEncontradoException();
        }

        var cursoExiste = cursosRepository.existsById(criarMatriculaRequest.getIdCurso());

        if (!cursoExiste){
            throw new CursoNaoEncontradoException();
        }

        var matriculaJaExiste = matriculasRepository.existsByIdAlunoAndIdCurso(criarMatriculaRequest.getIdAluno(), criarMatriculaRequest.getIdCurso());

        if (matriculaJaExiste){
            throw new MatriculaJaExisteException();
        }

        var novaMatricula = MatriculaEntity.builder()
                .id(UUID.randomUUID())
                .idAluno(criarMatriculaRequest.getIdAluno())
                .idCurso(criarMatriculaRequest.getIdCurso())
                .dataMatricula(LocalDateTime.now())
                .status(StatusCurso.ATIVO)
                .aulasConcluidas(new ArrayList<>())
                .build();

        var matriculaSalva = matriculasRepository.save(novaMatricula);

        return matriculaMapper.toMatriculaResponse(matriculaMapper.toMatriculaDomain(matriculaSalva));

    }

    public void cancelarMatricula(UUID id){

        var matricula = matriculasRepository.findById(id)
                .orElseThrow(MatriculaNaoEncontradaException::new);

        matricula.setStatus(StatusCurso.INATIVO);

        matriculasRepository.save(matricula);

    }

    // Issue #3 - lista os cursos em que o aluno está/esteve matriculado
    public List<CursoMatriculadoResponse> listarCursosMatriculados(Long idAluno) {

        var matriculas = matriculasRepository.findByIdAluno(idAluno);

        return matriculas.stream()
                .map(matricula -> {
                    var curso = cursosRepository.findById(matricula.getIdCurso())
                            .orElseThrow(CursoNaoEncontradoException::new);

                    return new CursoMatriculadoResponse(
                            curso.getId(),
                            curso.getNome(),
                            matricula.getStatus(),
                            matricula.getDataMatricula()
                    );
                })
                .toList();
    }

}
