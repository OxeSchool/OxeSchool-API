package com.oxeschool.api.controllers;

import com.oxeschool.api.dtos.tokens.TokensResponse;
import com.oxeschool.api.services.RefreshService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refresh")
public class RefreshController {

    final private RefreshService refreshService;

    public RefreshController(RefreshService refreshService) {
        this.refreshService = refreshService;
    }

    @PostMapping
    public ResponseEntity<TokensResponse> refresh(@RequestHeader("Authorization") String token){

        return ResponseEntity.status(HttpStatus.OK).body(refreshService.refresh(token));
    }

}
