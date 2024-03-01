package com.example.jwt_security_authentication.dto;

import lombok.Data;

@Data
public class BearerToken {

    private String accessToken;
    private String tokenType;

    public BearerToken(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
