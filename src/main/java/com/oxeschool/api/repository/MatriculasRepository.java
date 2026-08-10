package com.oxeschool.api.repository;

import com.oxeschool.api.entity.MatriculaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MatriculasRepository extends MongoRepository<MatriculaEntity, UUID> {
}
