package com.example.ecommerce.service;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            var userObj = user.get();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getUserRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }


    public void loginUser() {
        // login user
    }

    public void logoutUser() {
        // logout user
    }

    public void updateUserInfo() {
        // update user
    }

    public void updateUserRole() {
        // update user role
    }

    public void deleteUser() {
        // delete user
    }

    private String[] getUserRoles(User user) {
        if (user.getRoles() == null) {
            return new String[]{"USER"};
        }

        return Arrays
                .stream(user.getRoles().split(","))
                .map(String::trim)
                .toArray(String[]::new);
    }
}
