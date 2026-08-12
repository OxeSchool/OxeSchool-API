package com.oxeschool.api.repository;

import com.oxeschool.api.entity.Curso.CursoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CursosRepository extends MongoRepository<CursoEntity, UUID> {

    Boolean existsByNomeAndIdProfessor(String nome, Long idProfessor);

}
