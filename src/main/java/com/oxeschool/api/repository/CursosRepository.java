package com.oxeschool.api.repository;

import com.oxeschool.api.entity.Curso.CursoEntity;
import com.oxeschool.api.enums.StatusCurso;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface CursosRepository extends MongoRepository<CursoEntity, UUID> {

    Boolean existsByNomeAndIdProfessor(String nome, Long idProfessor);

    List<CursoEntity> findByStatus(StatusCurso status);

}
