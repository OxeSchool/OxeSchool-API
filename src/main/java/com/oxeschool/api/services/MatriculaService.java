package com.oxeschool.api.services;

import com.oxeschool.api.dtos.matricula.AlunoMatriculadoResponse;
import com.oxeschool.api.dtos.matricula.CursoMatriculadoResponse;
import com.oxeschool.api.dtos.matricula.MatriculaResponse;
import com.oxeschool.api.dtos.matricula.CriarMatriculaRequest;
import com.oxeschool.api.entity.MatriculaEntity;
import com.oxeschool.api.enums.StatusCurso;
import com.oxeschool.api.exceptions.customs.aluno.AlunoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.curso.CursoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.curso.CursoNaoPertenceAoProfessorException;
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
import java.util.stream.Collectors;

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

    public List<AlunoMatriculadoResponse> listarAlunosMatriculados(UUID idCurso, Long idProfessorSolicitante) {

        var curso = cursosRepository.findById(idCurso)
                .orElseThrow(CursoNaoEncontradoException::new);

        if (!curso.getIdProfessor().equals(idProfessorSolicitante)) {
            throw new CursoNaoPertenceAoProfessorException();
        }

        var matriculasAtivas = matriculasRepository.findByIdCursoAndStatus(idCurso, StatusCurso.ATIVO);

        var totalAulas = curso.getModulos().stream()
                .mapToLong(modulo -> modulo.getAulas().size())
                .sum();

        return matriculasAtivas.stream()
                .map(matricula -> {

                    var aluno = alunosRepository.findById(matricula.getIdAluno())
                            .orElseThrow(AlunoNaoEncontradoException::new);

                    var progresso = totalAulas == 0
                            ? 0.0
                            : (matricula.getAulasConcluidas().size() * 100.0) / totalAulas;

                    return new AlunoMatriculadoResponse(
                            aluno.getId(),
                            aluno.getNome(),
                            aluno.getEmail(),
                            matricula.getStatus(),
                            progresso
                    );
                })
                .collect(Collectors.toList());
    }
    // Issue #3 - lista os cursos em que o aluno está matriculado
    public List<CursoMatriculadoResponse> listarCursosMatriculados(Long idAluno) {

        var matriculas = matriculasRepository.findByIdAlunoAndStatus(idAluno, StatusCurso.ATIVO);

        return matriculas.stream()
                .map(matricula -> {
                    var curso = cursosRepository.findByIdAndStatus(matricula.getIdCurso(), StatusCurso.ATIVO)
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
