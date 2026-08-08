package com.oxeschool.api.mappers;

import com.oxeschool.api.domain.ProfessorDomain;
import com.oxeschool.api.dtos.usuario.response.ProfessorResponse;
import com.oxeschool.api.entity.ProfessorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

    ProfessorDomain toProfessorDomain(ProfessorEntity professorEntity);

    ProfessorResponse toProfessorResponse(ProfessorDomain professorDomain);

}
