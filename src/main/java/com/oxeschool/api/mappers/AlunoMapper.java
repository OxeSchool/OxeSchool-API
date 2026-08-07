package com.oxeschool.api.mappers;

import com.oxeschool.api.domain.AlunoDomain;
import com.oxeschool.api.dtos.usuario.response.AlunoResponse;
import com.oxeschool.api.entity.AlunoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    AlunoDomain toAlunoDomain(AlunoEntity alunoEntity);

    AlunoResponse toAlunoResponse(AlunoDomain alunoDomain);

}
