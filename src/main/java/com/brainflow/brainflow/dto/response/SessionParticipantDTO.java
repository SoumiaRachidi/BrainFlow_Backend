package com.brainflow.brainflow.dto.response;

import com.brainflow.brainflow.entity.SessionStatus;
import com.brainflow.brainflow.entity.ParticipantStatus;
import java.time.LocalDateTime;

public class SessionParticipantDTO {

    private Long id;
    private String title;
    private String animator;
    private LocalDateTime date;
    private SessionStatus status;
    private ParticipantStatus participantStatus;

    public SessionParticipantDTO() {
    }

    public SessionParticipantDTO(Long id, String title, String animator, LocalDateTime date, SessionStatus status) {
        this.id = id;
        this.title = title;
        this.animator = animator;
        this.date = date;
        this.status = status;
    }

    public SessionParticipantDTO(Long id, String title, String animator, LocalDateTime date, SessionStatus status, ParticipantStatus participantStatus) {
        this.id = id;
        this.title = title;
        this.animator = animator;
        this.date = date;
        this.status = status;
        this.participantStatus = participantStatus;
    }

    public ParticipantStatus getParticipantStatus() {
        return participantStatus;
    }

    public void setParticipantStatus(ParticipantStatus participantStatus) {
        this.participantStatus = participantStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnimator() {
        return animator;
    }

    public void setAnimator(String animator) {
        this.animator = animator;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }
}
