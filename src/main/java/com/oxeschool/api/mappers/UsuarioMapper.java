package com.oxeschool.api.mappers;

import com.oxeschool.api.domain.UsuarioDomain;
import com.oxeschool.api.dtos.usuario.response.UsuarioResponse;
import com.oxeschool.api.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper{

    UsuarioDomain toUsuarioDomain(UsuarioEntity usuarioEntity);

    UsuarioResponse toUsuarioResponse(UsuarioDomain usuarioDomain);
}
