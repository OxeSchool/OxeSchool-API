package com.oxeschool.api.dtos.usuario.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {

    private String email;
    private String senha;

}
