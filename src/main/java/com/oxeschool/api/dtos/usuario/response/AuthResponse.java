package com.oxeschool.api.dtos.usuario.response;

import com.oxeschool.api.dtos.tokens.TokensResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {

    private TokensResponse tokens;
    private UsuarioResponse usuario;

}
