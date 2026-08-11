package com.oxeschool.api.services;

import com.oxeschool.api.dtos.tokens.TokensResponse;
import com.oxeschool.api.jwt.JwtService;
import org.springframework.stereotype.Service;

@Service
public class RefreshService {

    final private JwtService jwtService;
    final private RedisBlackListService redisBlackListService;

    public RefreshService(JwtService jwtService, RedisBlackListService redisBlackListService) {
        this.jwtService = jwtService;
        this.redisBlackListService = redisBlackListService;
    }

    public TokensResponse refresh(String refreshToken){

        refreshToken = jwtService.pegarToken(refreshToken);

        redisBlackListService.verificarSeEstaBlacklisted(jwtService.pegarTokenId(refreshToken));

        redisBlackListService.adicionarRefreshToken(jwtService.pegarTokenId(refreshToken), refreshToken);

        var tokenDecoded = jwtService.decodificarRefreshToken(refreshToken);

        return jwtService.criarTokens(tokenDecoded.getUserId(),tokenDecoded.getRole());

    }

}
