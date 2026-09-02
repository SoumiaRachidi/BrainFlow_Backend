package com.brainflow.brainflow.controller;

import com.brainflow.brainflow.dto.request.UserProfileUpdateRequestDTO;
import com.brainflow.brainflow.dto.request.UserRegistrationDto;
import com.brainflow.brainflow.dto.response.UserResponseDTO;
import com.brainflow.brainflow.entity.User;
import com.brainflow.brainflow.service.UserService;
import com.brainflow.brainflow.service.BrainstormingSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final BrainstormingSessionService brainstormingSessionService;

    public UserController(UserService userService, BrainstormingSessionService brainstormingSessionService) {
        this.userService = userService;
        this.brainstormingSessionService = brainstormingSessionService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        try {
            User registeredUser = userService.registerUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getSystemRole(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.isApproved()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMe(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserProfileUpdateRequestDTO updateRequest
    ) {
        try {
            User updatedUser = userService.updateProfile(userDetails.getUsername(), updateRequest);
            UserResponseDTO response = new UserResponseDTO(
                    updatedUser.getId(),
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getSystemRole(),
                    updatedUser.getCreatedAt(),
                    updatedUser.getUpdatedAt(),
                    updatedUser.isApproved()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/me/sessions")
    public ResponseEntity<?> getMySessions(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(brainstormingSessionService.getSessionsByUserEmail(userDetails.getUsername()));
    }

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<java.util.List<UserResponseDTO>> getAllUsers() {
        java.util.List<UserResponseDTO> users = userService.getAllUsers().stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getSystemRole(),
                        user.getCreatedAt(),
                        user.getUpdatedAt(),
                        user.isApproved()
                ))
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/approve")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveUser(@PathVariable Long id) {
        try {
            User approvedUser = userService.approveUser(id);
            UserResponseDTO response = new UserResponseDTO(
                    approvedUser.getId(),
                    approvedUser.getUsername(),
                    approvedUser.getEmail(),
                    approvedUser.getSystemRole(),
                    approvedUser.getCreatedAt(),
                    approvedUser.getUpdatedAt(),
                    approvedUser.isApproved()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/{id}/reject")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectUser(@PathVariable Long id) {
        try {
            userService.rejectUser(id);
            return ResponseEntity.ok("User request rejected and deleted");
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}