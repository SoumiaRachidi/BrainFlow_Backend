package com.brainflow.brainflow.dto.response;

import java.time.LocalDateTime;

public class IdeaCommentResponseDTO {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long ideaId;
    private Long userId;
    private String username;
    private String userEmail;
    private Long parentId;
    private boolean resolved;

    public IdeaCommentResponseDTO() {
    }

    public IdeaCommentResponseDTO(Long id, String content, LocalDateTime createdAt, Long ideaId, Long userId, String username, String userEmail, Long parentId, boolean resolved) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.ideaId = ideaId;
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
        this.parentId = parentId;
        this.resolved = resolved;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(Long ideaId) {
        this.ideaId = ideaId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}
