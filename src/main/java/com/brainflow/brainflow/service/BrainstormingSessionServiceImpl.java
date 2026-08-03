package com.brainflow.brainflow.service;

import com.brainflow.brainflow.entity.BrainstormingSession;
import com.brainflow.brainflow.entity.SessionStatus;
import com.brainflow.brainflow.repository.BrainstormingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrainstormingSessionServiceImpl implements BrainstormingSessionService {

    private final BrainstormingSessionRepository brainstormingSessionRepository;

    public BrainstormingSessionServiceImpl(BrainstormingSessionRepository brainstormingSessionRepository) {
        this.brainstormingSessionRepository = brainstormingSessionRepository;
    }

    @Override
    public List<BrainstormingSession> getAllSessions() {
        return brainstormingSessionRepository.findAll();
    }

    @Override
    public BrainstormingSession getSessionById(Long id) {
        return brainstormingSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));
    }

    @Override
    public BrainstormingSession createSession(BrainstormingSession session, String createdByEmail) {
        session.setId(null);
        session.setCreatedBy(createdByEmail);
        if (session.getStatus() == null) {
            session.setStatus(SessionStatus.ACTIVE);
        }
        return brainstormingSessionRepository.save(session);
    }

    @Override
    public void deleteSession(Long id) {
        if (!brainstormingSessionRepository.existsById(id)) {
            throw new RuntimeException("Session not found with id: " + id);
        }
        brainstormingSessionRepository.deleteById(id);
    }
}