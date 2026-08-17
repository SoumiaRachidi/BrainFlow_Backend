package com.brainflow.brainflow.repository;

import com.brainflow.brainflow.entity.BrainstormingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrainstormingSessionRepository extends JpaRepository<BrainstormingSession, Long> {

    List<BrainstormingSession> findByCreatedByUserId(Long createdByUserId);

    Optional<BrainstormingSession> findByInviteToken(String inviteToken);
}