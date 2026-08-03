package com.brainflow.brainflow.service;

import com.brainflow.brainflow.dto.request.UserRegistrationDto;
import com.brainflow.brainflow.entity.User;

public interface UserService {

    User registerUser(UserRegistrationDto registrationDto);

    User getUserByEmail(String email);
}