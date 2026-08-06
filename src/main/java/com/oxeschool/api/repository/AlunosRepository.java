package com.oxeschool.api.repository;

import com.oxeschool.api.entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunosRepository extends JpaRepository<AlunoEntity, Long> {

    Boolean existsByEmail(String email);

}
