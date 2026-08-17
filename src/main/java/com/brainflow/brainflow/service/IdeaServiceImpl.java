package com.brainflow.brainflow.service;

import com.brainflow.brainflow.entity.BrainstormingSession;
import com.brainflow.brainflow.entity.Idea;
import com.brainflow.brainflow.entity.User;
import com.brainflow.brainflow.repository.BrainstormingSessionRepository;
import com.brainflow.brainflow.repository.IdeaRepository;
import com.brainflow.brainflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final BrainstormingSessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Autowired
    public IdeaServiceImpl(IdeaRepository ideaRepository, BrainstormingSessionRepository sessionRepository, UserRepository userRepository) {
        this.ideaRepository = ideaRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Idea> getIdeasBySession(Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new RuntimeException("Session not found with id: " + sessionId);
        }
        return ideaRepository.findBySessionId(sessionId);
    }

    @Override
    public Idea createIdea(Long sessionId, Idea idea) {
        BrainstormingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + sessionId));
        idea.setSession(session);
        if (idea.getVotes() == null) {
            idea.setVotes(0);
        }
        return ideaRepository.save(idea);
    }

    @Override
    public Idea updateIdea(Long ideaId, Idea ideaDetails) {
        Idea existing = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found with id: " + ideaId));
        
        if (ideaDetails.getContent() != null) {
            existing.setContent(ideaDetails.getContent());
        }
        if (ideaDetails.getColor() != null) {
            existing.setColor(ideaDetails.getColor());
        }
        if (ideaDetails.getX() != null) {
            existing.setX(ideaDetails.getX());
        }
        if (ideaDetails.getY() != null) {
            existing.setY(ideaDetails.getY());
        }
        
        return ideaRepository.save(existing);
    }

    @Override
    public Idea toggleVote(Long ideaId, String userEmail) {
        Idea existing = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found with id: " + ideaId));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        
        User voterToRemove = null;
        for (User v : existing.getVoters()) {
            if (v.getId().equals(user.getId())) {
                voterToRemove = v;
                break;
            }
        }

        if (voterToRemove != null) {
            existing.getVoters().remove(voterToRemove);
        } else {
            existing.getVoters().add(user);
        }
        existing.setVotes(existing.getVoters().size());
        return ideaRepository.save(existing);
    }

    @Override
    public void deleteIdea(Long ideaId) {
        if (!ideaRepository.existsById(ideaId)) {
            throw new RuntimeException("Idea not found with id: " + ideaId);
        }
        ideaRepository.deleteById(ideaId);
    }
}
