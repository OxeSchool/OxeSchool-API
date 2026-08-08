package com.oxeschool.api.repository;

import com.oxeschool.api.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessoresRepository extends JpaRepository<ProfessorEntity, Long> {

    Boolean existsByEmail(String email);

}
