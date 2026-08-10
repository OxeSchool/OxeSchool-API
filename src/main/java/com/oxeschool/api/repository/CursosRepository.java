package com.oxeschool.api.repository;

import com.oxeschool.api.entity.Curso.Aulas;
import com.oxeschool.api.entity.Curso.CursoEntity;
import com.oxeschool.api.entity.Curso.Modulos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.UUID;

public interface CursosRepository extends MongoRepository<CursoEntity, UUID> {

    Boolean existsByNomeAndIdProfessor(String nome, Long idProfessor);

    @Update("{ '$push': { 'modulos': ?1 } }")
    @Query("{ '_id': ?0 }")
    void adicionarModulo(UUID idCurso, Modulos modulo);

    @Update("{ '$push': { 'modulos.$.aulas': ?2 } }")
    @Query("{ '_id': ?0, 'modulos.id': ?1 }")
    void adicionarAula(
            UUID idCurso,
            UUID idModulo,
            Aulas aula
    );

    @Update("{ '$pull': { 'modulos': { 'id': ?1 } } }")
    @Query("{ '_id': ?0 }")
    void removerModulo(
            UUID idCurso,
            UUID idModulo
    );

    @Update("{ '$pull': { 'modulos.$[modulo].aulas': { 'id': ?2 } } }")
    @Query("{ '_id': ?0 }")
    void removerAula(
            UUID idCurso,
            UUID idModulo,
            UUID idAula
    );

}
