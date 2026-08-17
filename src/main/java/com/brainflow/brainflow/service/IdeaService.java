package com.brainflow.brainflow.service;

import com.brainflow.brainflow.entity.Idea;
import java.util.List;

public interface IdeaService {
    List<Idea> getIdeasBySession(Long sessionId);
    Idea createIdea(Long sessionId, Idea idea);
    Idea updateIdea(Long ideaId, Idea ideaDetails);
    Idea toggleVote(Long ideaId, String userEmail);
    void deleteIdea(Long ideaId);
}
