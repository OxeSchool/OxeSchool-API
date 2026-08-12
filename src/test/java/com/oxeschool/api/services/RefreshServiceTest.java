package com.oxeschool.api.services;

import com.oxeschool.api.domain.TokenDecodificado;
import com.oxeschool.api.dtos.tokens.TokensResponse;
import com.oxeschool.api.exceptions.customs.token.TokenNaBlackListException;
import com.oxeschool.api.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshServiceTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private RedisBlackListService redisBlackListService;

    @InjectMocks
    private RefreshService refreshService;

    @Test
    void refresh_comTokenValido_deveBlacklistarAntigoEGerarNovosTokens() {
        var tokenDecoded = new TokenDecodificado("refreshId", 1L, "Professor");
        var novosTokens = new TokensResponse("novoAccess", "novoRefresh");

        when(jwtService.pegarToken("Bearer refresh123")).thenReturn("refresh123");
        when(jwtService.pegarTokenId("refresh123")).thenReturn("refreshId");
        when(jwtService.decodificarRefreshToken("refresh123")).thenReturn(tokenDecoded);
        when(jwtService.criarTokens(1L, "Professor")).thenReturn(novosTokens);

        var resultado = refreshService.refresh("Bearer refresh123");

        assertEquals(novosTokens, resultado);
        verify(redisBlackListService).verificarSeEstaBlacklisted("refreshId");
        verify(redisBlackListService).adicionarRefreshToken("refreshId", "refresh123");
        verify(jwtService, times(2)).pegarTokenId("refresh123");
        verify(jwtService).decodificarRefreshToken("refresh123");
    }

    @Test
    void refresh_comTokenBlacklisted_deveLancarTokenNaBlackListException() {
        when(jwtService.pegarToken("Bearer refresh123")).thenReturn("refresh123");
        when(jwtService.pegarTokenId("refresh123")).thenReturn("refreshId");
        doThrow(new TokenNaBlackListException())
                .when(redisBlackListService).verificarSeEstaBlacklisted("refreshId");

        assertThrows(TokenNaBlackListException.class,
                () -> refreshService.refresh("Bearer refresh123"));

        verify(redisBlackListService, never()).adicionarRefreshToken(anyString(), anyString());
        verify(jwtService, never()).decodificarRefreshToken(anyString());
    }
}