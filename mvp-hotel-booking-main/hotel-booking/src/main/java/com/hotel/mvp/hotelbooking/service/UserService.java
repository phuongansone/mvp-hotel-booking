package com.hotel.mvp.hotelbooking.service;

import com.hotel.mvp.hotelbooking.dto.RegisterUserRequest;
import com.hotel.mvp.hotelbooking.entity.User;

public interface UserService {
    /**
     * Register new user
     * @param createUserRequest request object
     * @return registered user
     */
    User registerUser(RegisterUserRequest createUserRequest);
}
