package com.oxeschool.api.services;

import com.oxeschool.api.dtos.curso.*;
import com.oxeschool.api.entity.Curso.Aula;
import com.oxeschool.api.entity.Curso.CursoEntity;
import com.oxeschool.api.entity.Curso.Modulo;
import com.oxeschool.api.enums.StatusCurso;
import com.oxeschool.api.exceptions.customs.curso.*;
import com.oxeschool.api.jwt.JwtService;
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
    final private JwtService jwtService;

    public CursosService(CursosRepository cursosRepository,
                         CursoMapper cursoMapper,
                         JwtService jwtService) {
        this.cursosRepository = cursosRepository;
        this.cursoMapper = cursoMapper;
        this.jwtService = jwtService;
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

    public CursoResponse editar(String token, UUID cursoId, EditarCursoRequest editarCursoRequest) {

        token = jwtService.pegarToken(token);

        var tokenDecodificado = jwtService.decodificarAccessToken(token);

        var curso = cursosRepository.findById(cursoId)
                .orElseThrow(CursoNaoEncontradoException::new);

        if (!curso.getIdProfessor().equals(tokenDecodificado.getUserId())){
            throw new CursoNaoPertenceAoProfessorException();
        }

        curso.setNome(editarCursoRequest.getNome());
        curso.setDescricao(editarCursoRequest.getDescricao());
        curso.setCategoria(editarCursoRequest.getCategoria());
        curso.setCargaHoraria(editarCursoRequest.getCargaHoraria());

        var cursoSalvo = cursosRepository.save(curso);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(cursoSalvo));
    }

    public CursoResponse editarStatus(String token, UUID cursoid, EditarStatusCursoRequest editarStatusCursoRequest) {

        token = jwtService.pegarToken(token);

        var tokenDecodificado = jwtService.decodificarAccessToken(token);

        var curso = cursosRepository.findById(cursoid)
                .orElseThrow(CursoNaoEncontradoException::new);

        if (!curso.getIdProfessor().equals(tokenDecodificado.getUserId())){
            throw new CursoNaoPertenceAoProfessorException();
        }

        curso.setStatus(editarStatusCursoRequest.getStatus());

        var cursoSalvo = cursosRepository.save(curso);

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

    public CursoResponse editarModulo(String token,UUID cursoId, UUID moduloId, EditarModuloRequest editarModuloRequest){

        token = jwtService.pegarToken(token);

        var tokenDecodificado = jwtService.decodificarAccessToken(token);

        var curso = cursosRepository.findById(cursoId)
                .orElseThrow(CursoNaoEncontradoException::new);

        if (!curso.getIdProfessor().equals(tokenDecodificado.getUserId())){
            throw new CursoNaoPertenceAoProfessorException();
        }

        var modulos = curso.getModulos();

        var modulo = modulos.stream()
                .filter(m -> m.getId().equals(moduloId))
                .findFirst()
                .orElseThrow(ModuloNaoEncontradoException::new);

        var moduloIndex = modulos.indexOf(modulo);

        modulo.setNome(editarModuloRequest.getNome());

        modulos.set(moduloIndex, modulo);

        curso.setModulos(modulos);

        var cursoSalvo = cursosRepository.save(curso);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(cursoSalvo));
    }

    public CursoResponse excluirModulo(String token, UUID cursoId, UUID moduloId){

        token = jwtService.pegarToken(token);

        var tokenDecodificado = jwtService.decodificarAccessToken(token);

        var curso = cursosRepository.findById(cursoId)
                .orElseThrow(CursoNaoEncontradoException::new);

        if (!curso.getIdProfessor().equals(tokenDecodificado.getUserId())){
            throw new CursoNaoPertenceAoProfessorException();
        }

        var modulos = curso.getModulos();

        var modulo = modulos.stream()
                .filter(m -> m.getId().equals(moduloId))
                .findFirst()
                .orElseThrow(ModuloNaoEncontradoException::new);

        modulos.remove(modulo);

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

    public CursoResponse editarAula(String token, UUID cursoId, UUID moduloId, UUID aulaId, EditarAulaRequest editarAulaRequest){

        token = jwtService.pegarToken(token);

        var tokenDecodificado = jwtService.decodificarAccessToken(token);

        var curso = cursosRepository.findById(cursoId)
                .orElseThrow(CursoNaoEncontradoException::new);

        if (!curso.getIdProfessor().equals(tokenDecodificado.getUserId())){
            throw new CursoNaoPertenceAoProfessorException();
        }

        var modulo = curso.getModulos()
                .stream()
                .filter(m -> m.getId().equals(moduloId))
                .findFirst()
                .orElseThrow(ModuloNaoEncontradoException::new);

        var moduloIndex = curso.getModulos().indexOf(modulo);

        var aula = modulo.getAulas()
                .stream()
                .filter(a -> a.getId().equals(aulaId))
                .findFirst()
                .orElseThrow(AulaNaoEncontrada::new);

        var aulaIndex = modulo.getAulas().indexOf(aula);

        aula.setTitulo(editarAulaRequest.getTitulo());
        aula.setTexto(editarAulaRequest.getTexto());
        aula.setVideoUrl(editarAulaRequest.getVideoUrl());

        var aulas = modulo.getAulas();

        aulas.set(aulaIndex, aula);

        var modulos = curso.getModulos();

        modulos.set(moduloIndex, modulo);

        curso.setModulos(modulos);

        var cursoSalvo = cursosRepository.save(curso);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(cursoSalvo));

    }

    public CursoResponse excluirAula(String token, UUID cursoId, UUID moduloId, UUID aulaId){

        token = jwtService.pegarToken(token);

        var tokenDecodificado = jwtService.decodificarAccessToken(token);

        var curso = cursosRepository.findById(cursoId)
                .orElseThrow(CursoNaoEncontradoException::new);

        if (!curso.getIdProfessor().equals(tokenDecodificado.getUserId())){
            throw new CursoNaoPertenceAoProfessorException();
        }

        var modulo = curso.getModulos()
                .stream()
                .filter(m -> m.getId().equals(moduloId))
                .findFirst()
                .orElseThrow(ModuloNaoEncontradoException::new);

        var moduloIndex = curso.getModulos().indexOf(modulo);

        var aula = modulo.getAulas()
                .stream()
                .filter(a -> a.getId().equals(aulaId))
                .findFirst()
                .orElseThrow(AulaNaoEncontrada::new);

        var aulaIndex = modulo.getAulas().indexOf(aula);

        var aulas = modulo.getAulas();

        aulas.remove(aulaIndex);

        modulo.setAulas(aulas);

        var modulos = curso.getModulos();

        modulos.set(moduloIndex, modulo);

        curso.setModulos(modulos);

        var cursoSalvo = cursosRepository.save(curso);

        return cursoMapper.toCursoResponse(cursoMapper.toCursoDomain(cursoSalvo));

    }

}
