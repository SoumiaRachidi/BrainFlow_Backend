package com.brainflow.brainflow.dto.response;

import com.brainflow.brainflow.entity.SystemRole;

public class UserProfileResponseDTO {

    private String email;
    private String username;
    private String systemRole;

    public UserProfileResponseDTO() {
    }

    public UserProfileResponseDTO(String email, String username, String systemRole) {
        this.email = email;
        this.username = username;
        this.systemRole = systemRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(String systemRole) {
        this.systemRole = systemRole;
    }
}