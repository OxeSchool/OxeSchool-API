package com.oxeschool.api.repository;

import com.oxeschool.api.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<UsuarioEntity, Long> {

    boolean existsByEmail(String email);

}