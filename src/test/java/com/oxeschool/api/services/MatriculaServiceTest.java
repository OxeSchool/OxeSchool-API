package com.oxeschool.api.services;

import com.oxeschool.api.domain.MatriculaDomain;
import com.oxeschool.api.dtos.matricula.CriarMatriculaRequest;
import com.oxeschool.api.dtos.matricula.MatriculaResponse;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    private MatriculasRepository matriculasRepository;
    @Mock
    private MatriculaMapper matriculaMapper;
    @Mock
    private AlunosRepository alunosRepository;
    @Mock
    private CursosRepository cursosRepository;

    @InjectMocks
    private MatriculaService matriculaService;

    @Test
    void criarMatricula_comAlunoECursoValidos_deveSalvarComStatusAtivo() {
        var matriculaId = UUID.randomUUID();
        var cursoId = UUID.randomUUID();
        var request = new CriarMatriculaRequest(1L, cursoId);

        var dataMatricula = LocalDateTime.now();
        var matriculaSalva = MatriculaEntity.builder()
                .id(matriculaId)
                .idAluno(1L)
                .idCurso(cursoId)
                .dataMatricula(dataMatricula)
                .status(StatusCurso.ATIVO)
                .aulasConcluidas(new ArrayList<>())
                .build();
        var domain = new MatriculaDomain(matriculaId, 1L, cursoId, new ArrayList<>(), StatusCurso.ATIVO, dataMatricula);
        var response = new MatriculaResponse(matriculaId, 1L, cursoId, new ArrayList<>(), StatusCurso.ATIVO, dataMatricula);

        when(alunosRepository.existsById(1L)).thenReturn(true);
        when(cursosRepository.existsById(cursoId)).thenReturn(true);
        when(matriculasRepository.existsByIdAlunoAndIdCurso(1L, cursoId)).thenReturn(false);
        when(matriculasRepository.save(any(MatriculaEntity.class))).thenReturn(matriculaSalva);
        when(matriculaMapper.toMatriculaDomain(any(MatriculaEntity.class))).thenReturn(domain);
        when(matriculaMapper.toMatriculaResponse(domain)).thenReturn(response);

        var resultado = matriculaService.criarMatricula(request);

        assertEquals(matriculaId, resultado.getId());
        assertEquals(StatusCurso.ATIVO, resultado.getStatus());
        assertEquals(1L, resultado.getIdAluno());
        verify(matriculasRepository).save(any(MatriculaEntity.class));
    }

    @Test
    void criarMatricula_comAlunoInexistente_deveLancarAlunoNaoEncontradoException() {
        var cursoId = UUID.randomUUID();
        var request = new CriarMatriculaRequest(99L, cursoId);

        when(alunosRepository.existsById(99L)).thenReturn(false);

        assertThrows(AlunoNaoEncontradoException.class, () -> matriculaService.criarMatricula(request));

        verify(matriculasRepository, never()).save(any());
    }

    @Test
    void criarMatricula_comCursoInexistente_deveLancarCursoNaoEncontradoException() {
        var cursoId = UUID.randomUUID();
        var request = new CriarMatriculaRequest(1L, cursoId);

        when(alunosRepository.existsById(1L)).thenReturn(true);
        when(cursosRepository.existsById(cursoId)).thenReturn(false);

        assertThrows(CursoNaoEncontradoException.class, () -> matriculaService.criarMatricula(request));

        verify(matriculasRepository, never()).save(any());
    }

    @Test
    void criarMatricula_comMatriculaDuplicada_deveLancarMatriculaJaExisteException() {
        var cursoId = UUID.randomUUID();
        var request = new CriarMatriculaRequest(1L, cursoId);

        when(alunosRepository.existsById(1L)).thenReturn(true);
        when(cursosRepository.existsById(cursoId)).thenReturn(true);
        when(matriculasRepository.existsByIdAlunoAndIdCurso(1L, cursoId)).thenReturn(true);

        assertThrows(MatriculaJaExisteException.class, () -> matriculaService.criarMatricula(request));

        verify(matriculasRepository, never()).save(any());
    }

    @Test
    void cancelarMatricula_comMatriculaExistente_deveMarcarComoInativoESalvar() {
        var matriculaId = UUID.randomUUID();
        var cursoId = UUID.randomUUID();
        var matricula = MatriculaEntity.builder()
                .id(matriculaId)
                .idAluno(1L)
                .idCurso(cursoId)
                .status(StatusCurso.ATIVO)
                .aulasConcluidas(new ArrayList<>())
                .build();

        when(matriculasRepository.findById(matriculaId)).thenReturn(Optional.of(matricula));

        matriculaService.cancelarMatricula(matriculaId);

        assertEquals(StatusCurso.INATIVO, matricula.getStatus());
        verify(matriculasRepository).save(matricula);
    }

    @Test
    void cancelarMatricula_comMatriculaInexistente_deveLancarMatriculaNaoEncontradaException() {
        var matriculaId = UUID.randomUUID();

        when(matriculasRepository.findById(matriculaId)).thenReturn(Optional.empty());

        assertThrows(MatriculaNaoEncontradaException.class, () -> matriculaService.cancelarMatricula(matriculaId));

        verify(matriculasRepository, never()).save(any());
    }
}