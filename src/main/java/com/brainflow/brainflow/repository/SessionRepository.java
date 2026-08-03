package com.brainflow.brainflow.repository;

import com.brainflow.brainflow.entity.BrainstormSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<BrainstormSession, Long> {

    Optional<BrainstormSession> findByInviteToken(String inviteToken);
}
