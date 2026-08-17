package com.brainflow.brainflow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "introduction_slides")
public class IntroductionSlide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "slide_order", nullable = false)
    private Integer slideOrder;

    public IntroductionSlide() {
    }

    public IntroductionSlide(Long id, Long sessionId, String title, String content, Integer slideOrder) {
        this.id = id;
        this.sessionId = sessionId;
        this.title = title;
        this.content = content;
        this.slideOrder = slideOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSlideOrder() {
        return slideOrder;
    }

    public void setSlideOrder(Integer slideOrder) {
        this.slideOrder = slideOrder;
    }
}
