package com.oxeschool.api.services;

import com.oxeschool.api.dtos.tokens.TokensRequest;
import com.oxeschool.api.jwt.JwtService;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {

    final private RedisBlackListService redisBlackListService;
    final private JwtService jwtService;

    public LogoutService(RedisBlackListService redisBlackListService,
                          JwtService jwtService) {
        this.redisBlackListService = redisBlackListService;
        this.jwtService = jwtService;
    }

    public void logout(TokensRequest tokensRequest){

        redisBlackListService.adicionarAccessToken(jwtService.pegarTokenId(tokensRequest.getAccessToken()), tokensRequest.getAccessToken());
        redisBlackListService.adicionarRefreshToken(jwtService.pegarTokenId(tokensRequest.getRefreshToken()), tokensRequest.getRefreshToken());

    }


}
