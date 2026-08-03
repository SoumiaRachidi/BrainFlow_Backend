package com.brainflow.brainflow.service;

import com.brainflow.brainflow.dto.request.LoginRequestDTO;
import com.brainflow.brainflow.dto.response.JwtResponseDTO;
import com.brainflow.brainflow.dto.response.UserProfileResponseDTO;

public interface AuthService {

    JwtResponseDTO authenticate(LoginRequestDTO loginRequest);

    UserProfileResponseDTO getProfileByEmail(String email);

}