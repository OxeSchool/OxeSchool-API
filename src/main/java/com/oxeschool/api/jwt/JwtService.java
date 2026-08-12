package com.oxeschool.api.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oxeschool.api.domain.TokenDecodificado;
import com.oxeschool.api.dtos.tokens.TokensResponse;
import com.oxeschool.api.exceptions.customs.token.TokenExpiradoException;
import com.oxeschool.api.exceptions.customs.token.TokenInvalidoException;
import com.oxeschool.api.exceptions.customs.token.TokenTipoInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
public class JwtService {

    private final Algorithm algorithm;
    private final String issuer;
    private final String audience;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.audience}") String audience
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.audience = audience;
    }

    private String criarAccessToken(Long id, String role){

        Instant expireTimeAccess = Instant.now()
                .plus(Duration.ofMinutes(15));

        String accessTokenId = UUID.randomUUID().toString();

        return JWT.create()
                .withJWTId(accessTokenId)
                .withClaim("id", id)
                .withClaim("role", role)
                .withClaim("type", "Access")
                .withExpiresAt(expireTimeAccess)
                .withAudience(audience)
                .withIssuer(issuer)
                .sign(algorithm);

    }

    private String criarRefreshToken(Long id, String role){

        Instant expireTimeRefresh = Instant.now()
                .plus(Duration.ofDays(7));

        String refreshTokenId = UUID.randomUUID().toString();

        return JWT.create()
                .withJWTId(refreshTokenId)
                .withClaim("id", id)
                .withClaim("role", role)
                .withClaim("type", "Refresh")
                .withExpiresAt(expireTimeRefresh)
                .withAudience(audience)
                .withIssuer(issuer)
                .sign(algorithm);

    }

    public TokensResponse criarTokens(Long id, String role){

        return new TokensResponse(
                criarAccessToken(id,role), criarRefreshToken(id, role)
        );
    }

    public TokenDecodificado decodificarAccessToken(String token){

        try{

            DecodedJWT verifier = JWT.require(algorithm)
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);

            if (!verifier.getClaim("type").asString().equals("Access")){
                throw new TokenTipoInvalidoException("Access");
            }

            return new TokenDecodificado(verifier.getId(),verifier.getClaim("id").asLong(), verifier.getClaim("role").asString());

        } catch (com.auth0.jwt.exceptions.TokenExpiredException exception){
            throw new TokenExpiradoException();
        } catch (JWTVerificationException exception){
            throw new TokenInvalidoException();
        }

    }

    public TokenDecodificado decodificarRefreshToken(String token){

        try{

            DecodedJWT verifier = JWT.require(algorithm)
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);

            if (!verifier.getClaim("type").asString().equals("Refresh")){
                throw new TokenTipoInvalidoException("Refresh");
            }

            return new TokenDecodificado(verifier.getId(),verifier.getClaim("id").asLong(), verifier.getClaim("role").asString());

        } catch (TokenExpiredException exception){
            System.out.println(exception.getMessage());
            throw new TokenExpiradoException();
        } catch (JWTVerificationException exception){
            throw new TokenInvalidoException();
        }

    }

    public String pegarTokenId(String token){

        try {

            DecodedJWT decoded = JWT.decode(token);

            return decoded.getId();

        } catch (JWTDecodeException exception){
            throw new TokenInvalidoException();
        }


    }

    public String pegarToken(String rawToken){

        if (!rawToken.startsWith("Bearer ")){
            throw new TokenInvalidoException();
        }

        return rawToken.replace("Bearer ", "");

    }

}
