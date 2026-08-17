package com.brainflow.brainflow.service;

import com.brainflow.brainflow.entity.SessionDecision;
import com.brainflow.brainflow.repository.SessionDecisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class SessionDecisionServiceImpl implements SessionDecisionService {

    private final SessionDecisionRepository repository;

    @Autowired
    public SessionDecisionServiceImpl(SessionDecisionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SessionDecision> getDecisionsBySession(Long sessionId) {
        return repository.findBySessionIdOrderByCreatedAtAsc(sessionId);
    }

    @Override
    public SessionDecision addDecision(Long sessionId, String content, String decisionType, Long ideaId) {
        SessionDecision decision = new SessionDecision();
        decision.setSessionId(sessionId);
        decision.setContent(content);
        decision.setDecisionType(decisionType);
        decision.setIdeaId(ideaId);
        return repository.save(decision);
    }

    @Override
    public void deleteDecision(Long decisionId) {
        repository.deleteById(decisionId);
    }
}
