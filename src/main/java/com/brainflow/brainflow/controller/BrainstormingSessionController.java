package com.brainflow.brainflow.controller;

import com.brainflow.brainflow.entity.BrainstormingSession;
import com.brainflow.brainflow.dto.response.SessionParticipantDTO;
import com.brainflow.brainflow.service.BrainstormingSessionService;
import com.brainflow.brainflow.dto.response.ParticipantResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "http://localhost:3000")
public class BrainstormingSessionController {

    private final BrainstormingSessionService brainstormingSessionService;

    public BrainstormingSessionController(BrainstormingSessionService brainstormingSessionService) {
        this.brainstormingSessionService = brainstormingSessionService;
    }

    @GetMapping
    public ResponseEntity<List<BrainstormingSession>> getAllSessions() {
        return ResponseEntity.ok(brainstormingSessionService.getAllSessions());
    }

    @PostMapping
    public ResponseEntity<BrainstormingSession> createSession(@RequestBody BrainstormingSession session, Authentication authentication) {
        BrainstormingSession createdSession = brainstormingSessionService.createSession(session, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        brainstormingSessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/join/{token}")
    public ResponseEntity<SessionParticipantDTO> getSessionByToken(@PathVariable String token, Authentication authentication) {
        String email = (authentication != null) ? authentication.getName() : null;
        return ResponseEntity.ok(brainstormingSessionService.getSessionParticipantByToken(token, email));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BrainstormingSession> updateSessionStatus(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
        String statusStr = body.get("status");
        if (statusStr == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            com.brainflow.brainflow.entity.SessionStatus status = com.brainflow.brainflow.entity.SessionStatus.valueOf(statusStr.toUpperCase());
            BrainstormingSession updated = brainstormingSessionService.updateSessionStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantResponseDTO>> getWaitingParticipants(@PathVariable Long id) {
        return ResponseEntity.ok(brainstormingSessionService.getWaitingParticipants(id));
    }

    @PutMapping("/{id}/participants/{userId}/approve")
    public ResponseEntity<Void> approveParticipant(@PathVariable Long id, @PathVariable Long userId) {
        brainstormingSessionService.approveParticipant(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/participants/{userId}/reject")
    public ResponseEntity<Void> rejectParticipant(@PathVariable Long id, @PathVariable Long userId) {
        brainstormingSessionService.rejectParticipant(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/terminate")
    public ResponseEntity<?> terminateSession(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
        }
        try {
            BrainstormingSession terminated = brainstormingSessionService.terminateSession(id, authentication.getName());
            return ResponseEntity.ok(terminated);
        } catch (org.springframework.web.server.ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}