package com.oxeschool.api.services;

import com.oxeschool.api.exceptions.customs.token.TokenNaBlackListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisBlackListServiceTest {

    @Mock
    private StringRedisTemplate stringRedisTemplate;
    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RedisBlackListService redisBlackListService;

    @Test
    void adicionarAccessToken_deveSalvarComTtlDe15Minutos() {
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);

        redisBlackListService.adicionarAccessToken("jwtIdAccess", "accessToken");

        verify(valueOperations).set("jwtIdAccess", "accessToken", Duration.ofMinutes(15));
    }

    @Test
    void adicionarRefreshToken_deveSalvarComTtlDe7Dias() {
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);

        redisBlackListService.adicionarRefreshToken("jwtIdRefresh", "refreshToken");

        verify(valueOperations).set("jwtIdRefresh", "refreshToken", Duration.ofDays(7));
    }

    @Test
    void verificarSeEstaBlacklisted_comTokenPresente_deveLancarTokenNaBlackListException() {
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("jwtIdAccess")).thenReturn("accessToken");

        assertThrows(TokenNaBlackListException.class,
                () -> redisBlackListService.verificarSeEstaBlacklisted("jwtIdAccess"));
    }

    @Test
    void verificarSeEstaBlacklisted_comTokenAusente_naoDeveLancar() {
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("jwtIdAccess")).thenReturn(null);

        assertDoesNotThrow(() -> redisBlackListService.verificarSeEstaBlacklisted("jwtIdAccess"));
    }
}