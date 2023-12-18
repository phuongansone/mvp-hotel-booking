package com.hotel.mvp.hotelbooking.service.impl;

import com.hotel.mvp.hotelbooking.dto.RegisterUserRequest;
import com.hotel.mvp.hotelbooking.entity.User;
import com.hotel.mvp.hotelbooking.entity.UserRole;
import com.hotel.mvp.hotelbooking.exception.UserRegistrationException;
import com.hotel.mvp.hotelbooking.repository.UserRepository;
import com.hotel.mvp.hotelbooking.repository.UserRoleRepository;
import com.hotel.mvp.hotelbooking.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;

    @Transactional
    public User registerUser(RegisterUserRequest createUserRequest) {
        // Check if the username is already taken
        if (userRepository.findByUsername(createUserRequest.getUsername()).isPresent()) {
            throw new UserRegistrationException("Username is already taken");
        }

        // Create a new user
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        // You should hash the password before storing it in the database (e.g., using BCryptPasswordEncoder)
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        // Create user role
        UserRole userRole = new UserRole();
        userRole.setUsername(user.getUsername());
        userRole.setAuthority("CUSTOMER");
        userRoleRepository.save(userRole);

        // Save the user to the database
        return userRepository.save(user);
    }
}
