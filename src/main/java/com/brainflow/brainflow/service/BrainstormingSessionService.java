package com.brainflow.brainflow.service;

import com.brainflow.brainflow.entity.BrainstormingSession;
import com.brainflow.brainflow.entity.SessionStatus;
import com.brainflow.brainflow.dto.response.SessionParticipantDTO;
import com.brainflow.brainflow.dto.response.ParticipantResponseDTO;

import java.util.List;

public interface BrainstormingSessionService {

    List<BrainstormingSession> getAllSessions();

    BrainstormingSession getSessionById(Long id);

    BrainstormingSession createSession(BrainstormingSession session, String createdByEmail);

    void deleteSession(Long id);

    SessionParticipantDTO getSessionParticipantByToken(String token, String userEmail);

    BrainstormingSession updateSessionStatus(Long id, SessionStatus status);

    List<BrainstormingSession> getSessionsByUserEmail(String email);

    List<ParticipantResponseDTO> getWaitingParticipants(Long sessionId);

    void approveParticipant(Long sessionId, Long userId);

    void rejectParticipant(Long sessionId, Long userId);

    BrainstormingSession terminateSession(Long id, String userEmail);
}