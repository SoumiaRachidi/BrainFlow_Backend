package com.brainflow.brainflow.repository;

import com.brainflow.brainflow.entity.ParticipantStatus;
import com.brainflow.brainflow.entity.SessionParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionParticipantRepository extends JpaRepository<SessionParticipant, Long> {

    Optional<SessionParticipant> findBySessionIdAndUserId(Long sessionId, Long userId);

    List<SessionParticipant> findBySessionIdAndStatus(Long sessionId, ParticipantStatus status);

    List<SessionParticipant> findByUserId(Long userId);
}
