package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.tokens.TokensRequest;
import com.oxeschool.api.dtos.usuario.request.LogoutRequest;
import com.oxeschool.api.jwt.JwtService;
import com.oxeschool.api.services.LogoutService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    final private LogoutService logoutService;

    public LogoutController(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @PostMapping
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accesstoken, @Valid @RequestBody LogoutRequest refreshToken){

        logoutService.logout(new TokensRequest(accesstoken,refreshToken.getRefreshToken()));

        return ResponseEntity.noContent().build();

    }

}
