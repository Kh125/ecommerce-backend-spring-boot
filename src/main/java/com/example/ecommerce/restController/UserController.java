package com.example.ecommerce.restController;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.AuthRequestDto;
import com.example.ecommerce.dto.AuthResponseDto;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserCRUDService;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.webToken.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserCRUDService userCRUDService;
    UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController(
        UserCRUDService userCRUDService,
        UserService userService,
        JwtService jwtService,
        AuthenticationManager authenticationManager
    ) {
        this.userCRUDService = userCRUDService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;   
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse sResponse) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequestDto.getUsername(),
                    authRequestDto.getPassword()
                )
            );

            if (authentication.isAuthenticated()) {
                User userDetails = userCRUDService.getUserInfo(authRequestDto.getUsername());
                String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());
                Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

                AuthResponseDto response = new AuthResponseDto(
                    jwtService.generateAccessToken(userDetails.getUsername()),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getRoles()
                );
                
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setSecure(true);
                refreshTokenCookie.setPath("/");
                sResponse.addCookie(refreshTokenCookie);

                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                throw new UsernameNotFoundException("Invalid Credentials");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        User user = userCRUDService.registerUser(userDto);
        
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("update-roles/{userId}")
    public ResponseEntity<User> updateRoles(@RequestBody UserDto userDto, @PathVariable Long userId) {
        User user = userCRUDService.updateRoles(userDto, userId);
        
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
