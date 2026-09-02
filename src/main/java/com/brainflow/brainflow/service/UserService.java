package com.brainflow.brainflow.service;

import com.brainflow.brainflow.dto.request.UserProfileUpdateRequestDTO;
import com.brainflow.brainflow.dto.request.UserRegistrationDto;
import com.brainflow.brainflow.entity.User;

import java.util.List;

public interface UserService {

    User registerUser(UserRegistrationDto registrationDto);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User approveUser(Long id);

    void rejectUser(Long id);

    User updateProfile(String currentEmail, UserProfileUpdateRequestDTO updateRequest);
}