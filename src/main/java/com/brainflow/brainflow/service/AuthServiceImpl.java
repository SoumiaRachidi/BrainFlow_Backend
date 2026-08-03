package com.brainflow.brainflow.service;

import com.brainflow.brainflow.dto.request.LoginRequestDTO;
import com.brainflow.brainflow.dto.response.JwtResponseDTO;
import com.brainflow.brainflow.dto.response.UserProfileResponseDTO;
import com.brainflow.brainflow.entity.User;
import com.brainflow.brainflow.repository.UserRepository;
import com.brainflow.brainflow.config.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public JwtResponseDTO authenticate(LoginRequestDTO loginRequest) {
        Optional<User> maybeUser = userRepository.findByEmail(loginRequest.getEmail());
        if (maybeUser.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }

        User user = maybeUser.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        List<String> roles = new ArrayList<>();
        if (user.getSystemRole() != null) {
            roles.add(user.getSystemRole().name());
        }

        String token = jwtUtil.generateToken(user.getEmail(), roles);
        return new JwtResponseDTO(token);
    }

    @Override
    public UserProfileResponseDTO getProfileByEmail(String email) {
        Optional<User> maybeUser = userRepository.findByEmail(email);
        if (maybeUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = maybeUser.get();
        return new UserProfileResponseDTO(user.getEmail(), user.getUsername(), user.getSystemRole() == null ? null : user.getSystemRole().name());
    }
}