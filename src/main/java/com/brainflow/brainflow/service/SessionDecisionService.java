package com.brainflow.brainflow.service;

import com.brainflow.brainflow.entity.SessionDecision;
import java.util.List;

public interface SessionDecisionService {
    List<SessionDecision> getDecisionsBySession(Long sessionId);
    SessionDecision addDecision(Long sessionId, String content, String decisionType, Long ideaId);
    void deleteDecision(Long decisionId);
}
