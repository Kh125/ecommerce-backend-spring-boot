package com.example.ecommerce.dto;

public class AuthResponseDto {
    public AuthResponseDto(String token) {
        this.token = token;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
