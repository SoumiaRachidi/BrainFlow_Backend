package com.brainflow.brainflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private String color;

    @Column(nullable = false)
    private Integer votes = 0;

    @Column(name = "x_coordinate", nullable = false)
    private Double x = 100.0;

    @Column(name = "y_coordinate", nullable = false)
    private Double y = 100.0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    @JsonIgnore
    private BrainstormingSession session;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "idea_votes",
        joinColumns = @JoinColumn(name = "idea_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<User> voters = new HashSet<>();

    @Transient
    private Boolean votedByMe = false;

    public Idea() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.votes == null) {
            this.votes = 0;
        }
        if (this.x == null) {
            this.x = 100.0;
        }
        if (this.y == null) {
            this.y = 100.0;
        }
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BrainstormingSession getSession() {
        return session;
    }

    public void setSession(BrainstormingSession session) {
        this.session = session;
    }

    public Set<User> getVoters() {
        return voters;
    }

    public void setVoters(Set<User> voters) {
        this.voters = voters;
    }

    public Boolean getVotedByMe() {
        return votedByMe;
    }

    public void setVotedByMe(Boolean votedByMe) {
        this.votedByMe = votedByMe;
    }
}
