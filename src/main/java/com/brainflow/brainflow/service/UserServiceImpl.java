package com.brainflow.brainflow.service;

import com.brainflow.brainflow.dto.request.UserProfileUpdateRequestDTO;
import com.brainflow.brainflow.dto.request.UserRegistrationDto;
import com.brainflow.brainflow.entity.NotificationType;
import com.brainflow.brainflow.entity.SystemRole;
import com.brainflow.brainflow.entity.User;
import com.brainflow.brainflow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
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
        
        SystemRole role = SystemRole.USER;
        boolean approved = true;

        if (registrationDto.getRole() != null && registrationDto.getRole().equalsIgnoreCase("ANIMATOR")) {
            role = SystemRole.ANIMATOR;
            approved = false;
        }

        user.setSystemRole(role);
        user.setApproved(approved);

        User savedUser = userRepository.save(user);

        // If an unapproved Animator registers, send interactive notification to all Admin users
        if (!approved) {
            List<User> admins = userRepository.findBySystemRole(SystemRole.ADMIN);
            for (User admin : admins) {
                notificationService.createActionableNotification(
                        admin.getEmail(),
                        "Demande d'Approbation Animateur",
                        "L'utilisateur " + savedUser.getUsername() + " (" + savedUser.getEmail() + ") demande la création d'un compte Animateur.",
                        NotificationType.ANIMATOR_APPROVAL_REQUEST,
                        "/admin-dashboard#users",
                        null,
                        savedUser.getId()
                );
            }
        }

        return savedUser;
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

    @Override
    public User approveUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setApproved(true);
        return userRepository.save(user);
    }

    @Override
    public void rejectUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public User updateProfile(String currentEmail, UserProfileUpdateRequestDTO updateRequest) {
        User user = getUserByEmail(currentEmail);

        if (updateRequest.getUsername() != null && !updateRequest.getUsername().isBlank()) {
            if (!user.getUsername().equalsIgnoreCase(updateRequest.getUsername()) && userRepository.existsByUsername(updateRequest.getUsername())) {
                throw new RuntimeException("Ce pseudonyme est déjà utilisé.");
            }
            user.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isBlank()) {
            if (!user.getEmail().equalsIgnoreCase(updateRequest.getEmail()) && userRepository.existsByEmail(updateRequest.getEmail())) {
                throw new RuntimeException("Cette adresse e-mail est déjà utilisée.");
            }
            user.setEmail(updateRequest.getEmail());
        }

        if (updateRequest.getNewPassword() != null && !updateRequest.getNewPassword().isBlank()) {
            if (updateRequest.getNewPassword().length() < 6) {
                throw new RuntimeException("Le mot de passe doit contenir au moins 6 caractères.");
            }
            user.setPasswordHash(passwordEncoder.encode(updateRequest.getNewPassword()));
        }

        return userRepository.save(user);
    }
}