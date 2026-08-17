package com.brainflow.brainflow.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "session_participants",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"session_id", "user_id"})}
)
public class SessionParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private BrainstormingSession session;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipantStatus status = ParticipantStatus.WAITING;

    public SessionParticipant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BrainstormingSession getSession() {
        return session;
    }

    public void setSession(BrainstormingSession session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ParticipantStatus getStatus() {
        return status;
    }

    public void setStatus(ParticipantStatus status) {
        this.status = status;
    }
}
