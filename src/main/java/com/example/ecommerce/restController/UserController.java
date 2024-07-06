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
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequestDto.getUsername(),
                    authRequestDto.getPassword()
                )
            );

            if (authentication.isAuthenticated()) {
                User userDetails = userCRUDService.getUserInfo(authRequestDto.getUsername());
                
                AuthResponseDto response = new AuthResponseDto(
                    jwtService.generateToken(authRequestDto.getUsername()),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getRoles()
                );
                
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
