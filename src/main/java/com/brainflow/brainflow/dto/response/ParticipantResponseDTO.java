package com.brainflow.brainflow.dto.response;

public class ParticipantResponseDTO {
    private Long userId;
    private String email;
    private String username;
    private String status;

    public ParticipantResponseDTO() {
    }

    public ParticipantResponseDTO(Long userId, String email, String username, String status) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
