package com.oxeschool.api.repository;

import com.oxeschool.api.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursosRepository extends JpaRepository<CursoEntity, Long> {

    Boolean existsByNomeAndProfessorId(String nome, Long professorId);

}
