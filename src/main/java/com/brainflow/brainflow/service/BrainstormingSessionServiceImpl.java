package com.brainflow.brainflow.service;

import com.brainflow.brainflow.entity.BrainstormingSession;
import com.brainflow.brainflow.entity.SessionStatus;
import com.brainflow.brainflow.entity.User;
import com.brainflow.brainflow.entity.ParticipantStatus;
import com.brainflow.brainflow.entity.SessionParticipant;
import com.brainflow.brainflow.dto.response.SessionParticipantDTO;
import com.brainflow.brainflow.dto.response.ParticipantResponseDTO;
import com.brainflow.brainflow.repository.BrainstormingSessionRepository;
import com.brainflow.brainflow.repository.UserRepository;
import com.brainflow.brainflow.repository.SessionParticipantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BrainstormingSessionServiceImpl implements BrainstormingSessionService {

    private final BrainstormingSessionRepository brainstormingSessionRepository;
    private final UserRepository userRepository;
    private final SessionParticipantRepository sessionParticipantRepository;

    public BrainstormingSessionServiceImpl(
            BrainstormingSessionRepository brainstormingSessionRepository,
            UserRepository userRepository,
            SessionParticipantRepository sessionParticipantRepository) {
        this.brainstormingSessionRepository = brainstormingSessionRepository;
        this.userRepository = userRepository;
        this.sessionParticipantRepository = sessionParticipantRepository;
    }

    @Override
    public List<BrainstormingSession> getAllSessions() {
        List<BrainstormingSession> sessions = brainstormingSessionRepository.findAll();
        for (BrainstormingSession session : sessions) {
            if (session.getCreatedByUserId() != null) {
                userRepository.findById(session.getCreatedByUserId()).ifPresent(user -> {
                    session.setCreatorEmail(user.getEmail());
                });
            }
        }
        return sessions;
    }

    @Override
    public BrainstormingSession getSessionById(Long id) {
        BrainstormingSession session = brainstormingSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));
        if (session.getCreatedByUserId() != null) {
            userRepository.findById(session.getCreatedByUserId()).ifPresent(user -> {
                session.setCreatorEmail(user.getEmail());
            });
        }
        return session;
    }

    @Override
    public BrainstormingSession createSession(BrainstormingSession session, String createdByEmail) {
        session.setId(null);
        User creator = userRepository.findByEmail(createdByEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + createdByEmail));
        session.setCreatedByUserId(creator.getId());
        if (session.getStatus() == null) {
            session.setStatus(SessionStatus.ACTIVE);
        }
        BrainstormingSession saved = brainstormingSessionRepository.save(session);
        saved.setCreatorEmail(creator.getEmail());
        return saved;
    }

    @Override
    public void deleteSession(Long id) {
        if (!brainstormingSessionRepository.existsById(id)) {
            throw new RuntimeException("Session not found with id: " + id);
        }
        brainstormingSessionRepository.deleteById(id);
    }

    @Override
    public SessionParticipantDTO getSessionParticipantByToken(String token, String userEmail) {
        BrainstormingSession session = brainstormingSessionRepository.findByInviteToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found with token: " + token));

        if (session.getStatus() == SessionStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This session has been completed and is closed.");
        }

        User animator = userRepository.findById(session.getCreatedByUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animator not found for session"));

        ParticipantStatus pStatus = ParticipantStatus.APPROVED;

        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + userEmail));
            
            if (user.getId().equals(session.getCreatedByUserId())) {
                pStatus = ParticipantStatus.APPROVED;
            } else {
                SessionParticipant participant = sessionParticipantRepository.findBySessionIdAndUserId(session.getId(), user.getId())
                        .orElseGet(() -> {
                            SessionParticipant newParticipant = new SessionParticipant();
                            newParticipant.setSession(session);
                            newParticipant.setUser(user);
                            newParticipant.setStatus(ParticipantStatus.WAITING);
                            return sessionParticipantRepository.save(newParticipant);
                        });
                pStatus = participant.getStatus();
            }
        }

        return new SessionParticipantDTO(
                session.getId(),
                session.getTitle(),
                animator.getEmail(),
                session.getCreatedAt(),
                session.getStatus(),
                pStatus
        );
    }

    @Override
    public BrainstormingSession updateSessionStatus(Long id, SessionStatus status) {
        BrainstormingSession session = brainstormingSessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found with id: " + id));
        session.setStatus(status);
        return brainstormingSessionRepository.save(session);
    }

    @Override
    public List<BrainstormingSession> getSessionsByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email));
        
        List<BrainstormingSession> createdSessions = brainstormingSessionRepository.findByCreatedByUserId(user.getId());
        
        java.util.Set<BrainstormingSession> allSessions = new java.util.LinkedHashSet<>(createdSessions);
        
        List<SessionParticipant> joined = sessionParticipantRepository.findByUserId(user.getId());
        for (SessionParticipant p : joined) {
            allSessions.add(p.getSession());
        }
        
        List<BrainstormingSession> result = new java.util.ArrayList<>(allSessions);
        for (BrainstormingSession session : result) {
            if (session.getCreatedByUserId() != null) {
                userRepository.findById(session.getCreatedByUserId()).ifPresent(creator -> {
                    session.setCreatorEmail(creator.getEmail());
                });
            }
        }
        return result;
    }

    @Override
    public List<ParticipantResponseDTO> getWaitingParticipants(Long sessionId) {
        List<SessionParticipant> participants = sessionParticipantRepository.findBySessionIdAndStatus(sessionId, ParticipantStatus.WAITING);
        List<ParticipantResponseDTO> dtos = new java.util.ArrayList<>();
        for (SessionParticipant p : participants) {
            dtos.add(new ParticipantResponseDTO(
                    p.getUser().getId(),
                    p.getUser().getEmail(),
                    p.getUser().getUsername(),
                    p.getStatus().name()
            ));
        }
        return dtos;
    }

    @Override
    public void approveParticipant(Long sessionId, Long userId) {
        SessionParticipant participant = sessionParticipantRepository.findBySessionIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found"));
        participant.setStatus(ParticipantStatus.APPROVED);
        sessionParticipantRepository.save(participant);
    }
}