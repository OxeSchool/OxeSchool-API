package com.oxeschool.api.services;

import com.oxeschool.api.domain.CursoDomain;
import com.oxeschool.api.dtos.curso.CriarAulaRequest;
import com.oxeschool.api.dtos.curso.CriarCursoRequest;
import com.oxeschool.api.dtos.curso.CriarModuloRequest;
import com.oxeschool.api.dtos.curso.CursoResponse;
import com.oxeschool.api.entity.Curso.Aula;
import com.oxeschool.api.entity.Curso.CursoEntity;
import com.oxeschool.api.entity.Curso.Modulo;
import com.oxeschool.api.exceptions.customs.curso.AulaJaExisteException;
import com.oxeschool.api.exceptions.customs.curso.CursoJaExisteException;
import com.oxeschool.api.exceptions.customs.curso.CursoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.curso.ModuloJaExisteException;
import com.oxeschool.api.exceptions.customs.curso.ModuloNaoEncontradoException;
import com.oxeschool.api.mappers.CursoMapper;
import com.oxeschool.api.repository.CursosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CursosServiceTest {

    @Mock
    private CursosRepository cursosRepository;
    @Mock
    private CursoMapper cursoMapper;

    @InjectMocks
    private CursosService cursosService;

    @Test
    void criar_comDadosValidos_deveSalvarComModulosVazioERetornarResponse() {
        var cursoId = UUID.randomUUID();
        var request = new CriarCursoRequest("Java", 1L);

        var cursoSalvo = CursoEntity.builder()
                .id(cursoId).nome("Java").idProfessor(1L).modulos(new ArrayList<>()).build();
        var domain = new CursoDomain(cursoId, "Java", 1L, new ArrayList<>());
        var response = new CursoResponse(cursoId, "Java", 1L, new ArrayList<>());

        when(cursosRepository.existsByNomeAndIdProfessor("Java", 1L)).thenReturn(false);
        when(cursosRepository.save(any(CursoEntity.class))).thenReturn(cursoSalvo);
        when(cursoMapper.toCursoDomain(any(CursoEntity.class))).thenReturn(domain);
        when(cursoMapper.toCursoResponse(domain)).thenReturn(response);

        var resultado = cursosService.criar(request);

        assertEquals(cursoId, resultado.getId());
        assertEquals("Java", resultado.getNome());
        assertNotNull(resultado.getModulos());
        verify(cursosRepository).save(any(CursoEntity.class));
    }

    @Test
    void criar_comNomeRepetidoParaMesmoProfessor_deveLancarCursoJaExisteException() {
        var request = new CriarCursoRequest("Java", 1L);

        when(cursosRepository.existsByNomeAndIdProfessor("Java", 1L)).thenReturn(true);

        assertThrows(CursoJaExisteException.class, () -> cursosService.criar(request));

        verify(cursosRepository, never()).save(any());
    }

    @Test
    void pegarCursoPorId_comCursoExistente_deveRetornarResponse() {
        var cursoId = UUID.randomUUID();
        var curso = CursoEntity.builder()
                .id(cursoId).nome("Java").idProfessor(1L).modulos(new ArrayList<>()).build();
        var domain = new CursoDomain(cursoId, "Java", 1L, new ArrayList<>());
        var response = new CursoResponse(cursoId, "Java", 1L, new ArrayList<>());

        when(cursosRepository.findById(cursoId)).thenReturn(Optional.of(curso));
        when(cursoMapper.toCursoDomain(curso)).thenReturn(domain);
        when(cursoMapper.toCursoResponse(domain)).thenReturn(response);

        var resultado = cursosService.pegarCursoPorId(cursoId);

        assertEquals(cursoId, resultado.getId());
    }

    @Test
    void pegarCursoPorId_comCursoInexistente_deveLancarCursoNaoEncontradoException() {
        var cursoId = UUID.randomUUID();

        when(cursosRepository.findById(cursoId)).thenReturn(Optional.empty());

        assertThrows(CursoNaoEncontradoException.class, () -> cursosService.pegarCursoPorId(cursoId));
    }

    @Test
    void adicionarModulo_comModuloNovo_deveAdicionarESalvar() {
        var cursoId = UUID.randomUUID();
        var request = new CriarModuloRequest("Modulo 1");

        var curso = CursoEntity.builder()
                .id(cursoId).nome("Java").idProfessor(1L).modulos(new ArrayList<>()).build();
        var cursoComModulo = CursoEntity.builder()
                .id(cursoId).nome("Java").idProfessor(1L)
                .modulos(new ArrayList<>(List.of(Modulo.builder().nome("Modulo 1").aulas(new ArrayList<>()).build())))
                .build();
        var domain = new CursoDomain(cursoId, "Java", 1L, cursoComModulo.getModulos());
        var response = new CursoResponse(cursoId, "Java", 1L, cursoComModulo.getModulos());

        when(cursosRepository.findById(cursoId)).thenReturn(Optional.of(curso));
        when(cursosRepository.save(any(CursoEntity.class))).thenReturn(cursoComModulo);
        when(cursoMapper.toCursoDomain(any(CursoEntity.class))).thenReturn(domain);
        when(cursoMapper.toCursoResponse(domain)).thenReturn(response);

        var resultado = cursosService.adicionarModulo(cursoId, request);

        assertEquals(1, resultado.getModulos().size());
        assertEquals("Modulo 1", resultado.getModulos().get(0).getNome());
        verify(cursosRepository).save(any(CursoEntity.class));
    }

    @Test
    void adicionarModulo_comModuloDuplicado_deveLancarModuloJaExisteException() {
        var cursoId = UUID.randomUUID();
        var request = new CriarModuloRequest("Modulo 1");

        var curso = CursoEntity.builder()
                .id(cursoId).nome("Java").idProfessor(1L)
                .modulos(new ArrayList<>(List.of(
                        Modulo.builder().id(UUID.randomUUID()).nome("Modulo 1").aulas(new ArrayList<>()).build())))
                .build();

        when(cursosRepository.findById(cursoId)).thenReturn(Optional.of(curso));

        assertThrows(ModuloJaExisteException.class, () -> cursosService.adicionarModulo(cursoId, request));

        verify(cursosRepository, never()).save(any());
    }

    @Test
    void adicionarModulo_comCursoInexistente_deveLancarCursoNaoEncontradoException() {
        var cursoId = UUID.randomUUID();

        when(cursosRepository.findById(cursoId)).thenReturn(Optional.empty());

        assertThrows(CursoNaoEncontradoException.class,
                () -> cursosService.adicionarModulo(cursoId, new CriarModuloRequest("Modulo 1")));
    }

    @Test
    void adicionarAula_comAulaNova_deveAdicionarEESalvar() {
        var cursoId = UUID.randomUUID();
        var moduloId = UUID.randomUUID();
        var request = new CriarAulaRequest("Introducao", "Texto", "url-video");

        var modulo = Modulo.builder()
                .id(moduloId).nome("Modulo 1").aulas(new ArrayList<>()).build();
        var curso = CursoEntity.builder()
                .id(cursoId).nome("Java").idProfessor(1L)
                .modulos(new ArrayList<>(List.of(modulo))).build();

        var moduloComAula = Modulo.builder()
                .id(moduloId).nome("Modulo 1")
                .aulas(new ArrayList<>(List.of(Aula.builder().titulo("Introducao").texto("Texto").videoUrl("url-video").build())))
                .build();
        var cursoComAula = CursoEntity.builder()
                .id(cursoId).nome("Java").idProfessor(1L)
                .modulos(new ArrayList<>(List.of(moduloComAula))).build();
        var domain = new CursoDomain(cursoId, "Java", 1L, cursoComAula.getModulos());
        var response = new CursoResponse(cursoId, "Java", 1L, cursoComAula.getModulos());

        when(cursosRepository.findById(cursoId)).thenReturn(Optional.of(curso));
        when(cursosRepository.save(any(CursoEntity.class))).thenReturn(cursoComAula);
        when(cursoMapper.toCursoDomain(any(CursoEntity.class))).thenReturn(domain);
        when(cursoMapper.toCursoResponse(domain)).thenReturn(response);

        var resultado = cursosService.adicionarAula(cursoId, moduloId, request);

        assertEquals(1, resultado.getModulos().get(0).getAulas().size());
        assertEquals("Introducao", resultado.getModulos().get(0).getAulas().get(0).getTitulo());
        verify(cursosRepository).save(any(CursoEntity.class));
    }

    @Test
    void adicionarAula_comAulaDuplicada_deveLancarAulaJaExisteException() {
        var cursoId = UUID.randomUUID();
        var moduloId = UUID.randomUUID();
        var request = new CriarAulaRequest("Introducao", "Texto", "url-video");

        var modulo = Modulo.builder()
                .id(moduloId).nome("Modulo 1")
                .aulas(new ArrayList<>(List.of(
                        Aula.builder().titulo("Introducao").texto("Texto").videoUrl("url-video").build())))
                .build();
        var curso = CursoEntity.builder()
                .id(cursoId).nome("Java").idProfessor(1L)
                .modulos(new ArrayList<>(List.of(modulo))).build();

        when(cursosRepository.findById(cursoId)).thenReturn(Optional.of(curso));

        assertThrows(AulaJaExisteException.class, () -> cursosService.adicionarAula(cursoId, moduloId, request));

        verify(cursosRepository, never()).save(any());
    }

    @Test
    void adicionarAula_comModuloInexistente_deveLancarModuloNaoEncontradoException() {
        var cursoId = UUID.randomUUID();
        var moduloId = UUID.randomUUID();
        var request = new CriarAulaRequest("Introducao", "Texto", "url-video");

        var curso = CursoEntity.builder()
                .id(cursoId).nome("Java").idProfessor(1L)
                .modulos(new ArrayList<>(List.of(
                        Modulo.builder().id(UUID.randomUUID()).nome("Outro").aulas(new ArrayList<>()).build())))
                .build();

        when(cursosRepository.findById(cursoId)).thenReturn(Optional.of(curso));

        assertThrows(ModuloNaoEncontradoException.class,
                () -> cursosService.adicionarAula(cursoId, moduloId, request));
    }
}