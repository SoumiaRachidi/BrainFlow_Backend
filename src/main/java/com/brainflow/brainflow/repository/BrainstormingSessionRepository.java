package com.brainflow.brainflow.repository;

import com.brainflow.brainflow.entity.BrainstormingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrainstormingSessionRepository extends JpaRepository<BrainstormingSession, Long> {

    List<BrainstormingSession> findByCreatedBy(String createdBy);
}