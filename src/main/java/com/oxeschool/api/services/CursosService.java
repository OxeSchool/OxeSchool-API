package com.oxeschool.api.services;

import com.oxeschool.api.dtos.curso.CriarAulaRequest;
import com.oxeschool.api.dtos.curso.CriarCursoRequest;
import com.oxeschool.api.dtos.curso.CriarModuloRequest;
import com.oxeschool.api.dtos.curso.CursoResponse;
import com.oxeschool.api.entity.Curso.Aula;
import com.oxeschool.api.entity.Curso.CursoEntity;
import com.oxeschool.api.entity.Curso.Modulo;
import com.oxeschool.api.enums.StatusCurso;
import com.oxeschool.api.exceptions.customs.curso.*;
import com.oxeschool.api.mappers.CursoMapper;
import com.oxeschool.api.repository.CursosRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
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
                .descricao(criarCursoRequest.getDescricao())
                .idProfessor(criarCursoRequest.getIdProfessor())
                .categoria(criarCursoRequest.getCategoria())
                .cargaHoraria(criarCursoRequest.getCargaHoraria())
                .modulos(new ArrayList<>())
                .status(StatusCurso.ATIVO)
                .build();

        var cursoSalvo = cursosRepository.save(novoCurso);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(cursoSalvo));
    }

    public CursoResponse pegarCursoPorId(UUID id){

        var curso = cursosRepository.findById(id)
                .orElseThrow(CursoNaoEncontradoException::new);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(curso));

    }

    public CursoResponse adicionarModulo(UUID cursoId, CriarModuloRequest criarModuloRequest){

        var curso = cursosRepository.findById(cursoId)
                .orElseThrow(CursoNaoEncontradoException::new);

        var modulos = curso.getModulos();

        var novoModulo = Modulo.builder()
                .id(UUID.randomUUID())
                .nome(criarModuloRequest.getNome())
                .aulas(new ArrayList<>())
                .build();

        boolean existe = modulos.stream()
                .anyMatch(moduloExistente ->
                        moduloExistente.getNome().equalsIgnoreCase(novoModulo.getNome())
                );

        if (existe){
            throw new ModuloJaExisteException();
        }

        modulos.add(novoModulo);

        curso.setModulos(modulos);

        var cursoSalvo = cursosRepository.save(curso);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(cursoSalvo));
    }

    public CursoResponse adicionarAula(UUID cursoId, UUID moduloId, CriarAulaRequest criarAulaRequest){

        var curso = cursosRepository.findById(cursoId)
                .orElseThrow(CursoNaoEncontradoException::new);

        var modulo = curso.getModulos()
                .stream()
                .filter(m -> m.getId().equals(moduloId))
                .findFirst()
                .orElseThrow(ModuloNaoEncontradoException::new);

        var moduloIndex = curso.getModulos().indexOf(modulo);

        var novaAula = Aula.builder()
                .id(UUID.randomUUID())
                .titulo(criarAulaRequest.getTitulo())
                .texto(criarAulaRequest.getTexto())
                .videoUrl(criarAulaRequest.getVideoUrl())
                .build();

        boolean existe = modulo.getAulas().stream()
                .anyMatch(aulaExistente ->
                        Objects.equals(aulaExistente.getTitulo(), novaAula.getTitulo())
                                && Objects.equals(aulaExistente.getTexto(), novaAula.getTexto())
                                && Objects.equals(aulaExistente.getVideoUrl(), novaAula.getVideoUrl())
                );

        if (existe){
            throw new AulaJaExisteException();
        }

        var aulas = modulo.getAulas();

        aulas.add(novaAula);

        modulo.setAulas(aulas);

        var modulos = curso.getModulos();

        modulos.set(moduloIndex, modulo);

        curso.setModulos(modulos);

        var cursoSalvo = cursosRepository.save(curso);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(cursoSalvo));

    }

}
