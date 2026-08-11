package com.oxeschool.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.oxeschool.api.entity.MatriculaEntity;
import com.oxeschool.api.enums.StatusCurso;

public interface MatriculasRepository extends MongoRepository<MatriculaEntity, UUID> {

    Boolean existsByIdAlunoAndIdCurso(Long idAluno, UUID idCurso);

    List<MatriculaEntity> findByIdCursoAndStatus(UUID idCurso, StatusCurso status);

}  