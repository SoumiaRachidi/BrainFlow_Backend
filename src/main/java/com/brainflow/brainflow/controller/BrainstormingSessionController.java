package com.brainflow.brainflow.controller;

import com.brainflow.brainflow.entity.BrainstormingSession;
import com.brainflow.brainflow.service.BrainstormingSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
}