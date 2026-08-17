package com.brainflow.brainflow.service;

import com.brainflow.brainflow.dto.request.UserRegistrationDto;
import com.brainflow.brainflow.entity.SystemRole;
import com.brainflow.brainflow.entity.User;
import com.brainflow.brainflow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("An account with this email already exists: " + registrationDto.getEmail());
        }

        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RuntimeException("An account with this username already exists: " + registrationDto.getUsername());
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        user.setSystemRole(SystemRole.ANIMATOR);

        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}