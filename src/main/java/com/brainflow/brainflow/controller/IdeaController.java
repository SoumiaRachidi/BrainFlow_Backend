package com.brainflow.brainflow.controller;

import com.brainflow.brainflow.dto.request.CommentRequestDTO;
import com.brainflow.brainflow.dto.response.IdeaCommentResponseDTO;
import com.brainflow.brainflow.entity.Idea;
import com.brainflow.brainflow.entity.User;
import com.brainflow.brainflow.service.IdeaService;
import com.brainflow.brainflow.service.UserService;
import com.brainflow.brainflow.service.IdeaCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IdeaController {

    private final IdeaService ideaService;
    private final UserService userService;
    private final IdeaCommentService commentService;

    @Autowired
    public IdeaController(IdeaService ideaService, UserService userService, IdeaCommentService commentService) {
        this.ideaService = ideaService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/sessions/{sessionId}/ideas")
    public ResponseEntity<List<Idea>> getIdeas(@PathVariable Long sessionId, @AuthenticationPrincipal UserDetails userDetails) {
        List<Idea> ideas = ideaService.getIdeasBySession(sessionId);
        if (userDetails != null) {
            User user = userService.getUserByEmail(userDetails.getUsername());
            for (Idea idea : ideas) {
                idea.setVotedByMe(idea.getVoters().stream().anyMatch(v -> v.getId().equals(user.getId())));
            }
        }
        return ResponseEntity.ok(ideas);
    }

    @PostMapping("/sessions/{sessionId}/ideas")
    public ResponseEntity<Idea> createIdea(@PathVariable Long sessionId, @RequestBody Idea idea, @AuthenticationPrincipal UserDetails userDetails) {
        Idea created = ideaService.createIdea(sessionId, idea);
        if (userDetails != null) {
            User user = userService.getUserByEmail(userDetails.getUsername());
            created.setVotedByMe(created.getVoters().stream().anyMatch(v -> v.getId().equals(user.getId())));
        }
        return ResponseEntity.ok(created);
    }

    @PutMapping("/ideas/{ideaId}")
    public ResponseEntity<Idea> updateIdea(@PathVariable Long ideaId, @RequestBody Idea idea, @AuthenticationPrincipal UserDetails userDetails) {
        Idea updated = ideaService.updateIdea(ideaId, idea);
        if (userDetails != null) {
            User user = userService.getUserByEmail(userDetails.getUsername());
            updated.setVotedByMe(updated.getVoters().stream().anyMatch(v -> v.getId().equals(user.getId())));
        }
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/ideas/{ideaId}/vote")
    public ResponseEntity<Idea> voteIdea(@PathVariable Long ideaId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        String email = userDetails.getUsername();
        Idea updated = ideaService.toggleVote(ideaId, email);
        User user = userService.getUserByEmail(email);
        updated.setVotedByMe(updated.getVoters().stream().anyMatch(v -> v.getId().equals(user.getId())));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/ideas/{ideaId}")
    public ResponseEntity<Void> deleteIdea(@PathVariable Long ideaId) {
        ideaService.deleteIdea(ideaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ideas/{ideaId}/comments")
    public ResponseEntity<List<IdeaCommentResponseDTO>> getComments(@PathVariable Long ideaId) {
        return ResponseEntity.ok(commentService.getCommentsByIdea(ideaId));
    }

    @PostMapping("/ideas/{ideaId}/comments")
    public ResponseEntity<IdeaCommentResponseDTO> addComment(
            @PathVariable Long ideaId,
            @RequestBody CommentRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(commentService.addComment(ideaId, request, userDetails.getUsername()));
    }

    @PutMapping("/comments/{commentId}/resolve")
    public ResponseEntity<IdeaCommentResponseDTO> resolveComment(
            @PathVariable Long commentId,
            @RequestParam boolean resolved) {
        return ResponseEntity.ok(commentService.resolveComment(commentId, resolved));
    }
}
