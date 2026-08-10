package com.oxeschool.api.mappers;

import com.oxeschool.api.domain.CursoDomain;
import com.oxeschool.api.dtos.curso.CursoResponse;
import com.oxeschool.api.entity.Curso.CursoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    CursoDomain toCursoDomain(CursoEntity cursoEntity);

    CursoResponse toCursoResponse(CursoDomain cursoDomain);

}
