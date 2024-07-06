package com.example.ecommerce.dto;

public class AuthResponseDto {
    public AuthResponseDto(String token, String username, String email, String roles) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    private String token;

    private String username;

    private String email;

    private String roles;

    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles; 
    }
}
