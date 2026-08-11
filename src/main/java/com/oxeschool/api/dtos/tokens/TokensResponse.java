package com.oxeschool.api.dtos.tokens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TokensResponse {

    private String accessToken;
    private String refreshToken;

}
