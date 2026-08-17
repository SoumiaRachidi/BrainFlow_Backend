package com.brainflow.brainflow.repository;

import com.brainflow.brainflow.entity.SessionDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SessionDecisionRepository extends JpaRepository<SessionDecision, Long> {
    List<SessionDecision> findBySessionIdOrderByCreatedAtAsc(Long sessionId);
}
