package com.example.ecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;

@Service
public class UserCRUDService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserCRUDService(PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }
    
    public User registerUser(UserDto user) {
        User registeredUser = modelMapper.map(user, User.class);
        
        registeredUser.setPassword(passwordEncoder.encode(registeredUser.getPassword()));
        
        return userRepository.save(registeredUser);
    }
}
