package com.oxeschool.api.mappers;

import com.oxeschool.api.domain.MatriculaDomain;
import com.oxeschool.api.dtos.matricula.MatriculaResponse;
import com.oxeschool.api.entity.MatriculaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MatriculaMapper {

    MatriculaDomain toMatriculaDomain(MatriculaEntity matriculaEntity);

    MatriculaResponse toMatriculaResponse(MatriculaDomain matriculaDomain);

}
