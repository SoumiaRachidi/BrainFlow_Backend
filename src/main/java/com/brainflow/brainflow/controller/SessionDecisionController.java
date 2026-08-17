package com.brainflow.brainflow.controller;

import com.brainflow.brainflow.entity.SessionDecision;
import com.brainflow.brainflow.service.SessionDecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SessionDecisionController {

    private final SessionDecisionService service;

    @Autowired
    public SessionDecisionController(SessionDecisionService service) {
        this.service = service;
    }

    @GetMapping("/sessions/{sessionId}/decisions")
    public ResponseEntity<List<SessionDecision>> getDecisions(@PathVariable Long sessionId) {
        return ResponseEntity.ok(service.getDecisionsBySession(sessionId));
    }

    @PostMapping("/sessions/{sessionId}/decisions")
    public ResponseEntity<SessionDecision> addDecision(
            @PathVariable Long sessionId,
            @RequestParam String content,
            @RequestParam String decisionType,
            @RequestParam(required = false) Long ideaId) {
        return ResponseEntity.ok(service.addDecision(sessionId, content, decisionType, ideaId));
    }

    @DeleteMapping("/decisions/{decisionId}")
    public ResponseEntity<Void> deleteDecision(@PathVariable Long decisionId) {
        service.deleteDecision(decisionId);
        return ResponseEntity.noContent().build();
    }
}
