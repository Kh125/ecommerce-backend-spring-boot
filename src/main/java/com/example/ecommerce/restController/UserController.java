package com.example.ecommerce.restController;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.AuthRequestDto;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserCRUDService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserCRUDService userCRUDService;
    private final AuthenticationManager authenticationManager;

    public UserController(
        UserCRUDService userCRUDService,
        AuthenticationManager authenticationManager
    ) {
        this.userCRUDService = userCRUDService;
        this.authenticationManager = authenticationManager;   
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequestDto.getUsername(),
                    authRequestDto.getPassword()
                )
            );

            return ResponseEntity.ok(authentication.getPrincipal());
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
