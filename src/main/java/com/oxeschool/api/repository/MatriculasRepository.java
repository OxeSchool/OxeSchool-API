package com.oxeschool.api.repository;

import com.oxeschool.api.entity.MatriculaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface MatriculasRepository extends MongoRepository<MatriculaEntity, UUID> {

    Boolean existsByIdAlunoAndIdCurso(Long idAluno, UUID idCurso);

    List<MatriculaEntity> findByIdAluno(Long idAluno);
}
