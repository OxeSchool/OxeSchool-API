package com.oxeschool.api.dtos.tokens;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TokensRequest {

    @NotBlank(message = "access token é obrigatorio")
    private String accessToken;

    @NotBlank(message = "refresh token é obrigatorio")
    private String refreshToken;

}
