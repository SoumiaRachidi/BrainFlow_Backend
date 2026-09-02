package com.brainflow.brainflow.dto.response;

import com.brainflow.brainflow.entity.SystemRole;

import java.time.LocalDateTime;

public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private SystemRole systemRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean approved;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String username, String email, SystemRole systemRole, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.systemRole = systemRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.approved = true;
    }

    public UserResponseDTO(Long id, String username, String email, SystemRole systemRole, LocalDateTime createdAt, LocalDateTime updatedAt, boolean approved) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.systemRole = systemRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SystemRole getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
