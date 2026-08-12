package com.oxeschool.api.services;

import com.oxeschool.api.dtos.tokens.TokensRequest;
import com.oxeschool.api.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogoutServiceTest {

    @Mock
    private RedisBlackListService redisBlackListService;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LogoutService logoutService;

    @Test
    void logout_deveBlacklistarAccessERefreshTokens() {
        var tokensRequest = new TokensRequest("Bearer access123", "refresh123");

        when(jwtService.pegarToken("Bearer access123")).thenReturn("access123");
        when(jwtService.pegarTokenId("access123")).thenReturn("accessId");
        when(jwtService.pegarTokenId("refresh123")).thenReturn("refreshId");

        logoutService.logout(tokensRequest);

        verify(redisBlackListService).adicionarAccessToken("accessId", "Bearer access123");
        verify(redisBlackListService).adicionarRefreshToken("refreshId", "refresh123");
    }
}