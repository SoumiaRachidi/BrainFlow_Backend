package com.brainflow.brainflow.service;

import com.brainflow.brainflow.entity.BrainstormingSession;

import java.util.List;

public interface BrainstormingSessionService {

    List<BrainstormingSession> getAllSessions();

    BrainstormingSession getSessionById(Long id);

    BrainstormingSession createSession(BrainstormingSession session, String createdByEmail);

    void deleteSession(Long id);
}