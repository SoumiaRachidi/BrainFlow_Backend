package com.brainflow.brainflow.dto.request;

public class CommentRequestDTO {

    private String content;
    private Long parentId;

    public CommentRequestDTO() {
    }

    public CommentRequestDTO(String content, Long parentId) {
        this.content = content;
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
