package com.example.ecommerce.restController;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.AuthResponseDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserCRUDService;
import com.example.ecommerce.webToken.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserCRUDService userCRUDService;
    private final JwtService jwtService;

    public AuthController(
        UserCRUDService userCRUDService,
        JwtService jwtService
    ) {
        this.userCRUDService = userCRUDService;
        this.jwtService = jwtService;
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@CookieValue("refreshToken") String refreshToken, HttpServletResponse httpServletResponse) {        
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token is required"));
        }
        
        try {
            Claims claims = jwtService.getClaims(refreshToken);
            String username = claims.getSubject();
            User userDetails = userCRUDService.getUserInfo(username);

            AuthResponseDto response = new AuthResponseDto(
                    jwtService.generateAccessToken(username),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getRoles()
                );

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Username or password"));
        }
    }
}
