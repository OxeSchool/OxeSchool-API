package com.oxeschool.api.services;

import com.oxeschool.api.exceptions.customs.token.TokenNaBlackListException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisBlackListService {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisBlackListService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void adicionarAccessToken(String jwtId, String token) {

        stringRedisTemplate.opsForValue().set(jwtId, token, Duration.ofMinutes(15));
    }

    public void adicionarRefreshToken(String jwtId, String token) {
        stringRedisTemplate.opsForValue().set(jwtId, token, Duration.ofDays(7));
    }

    public void verificarSeEstaBlacklisted(String jwtId) {

        if (stringRedisTemplate.opsForValue().get(jwtId) != null){
            throw new TokenNaBlackListException();
        }
    }

}
