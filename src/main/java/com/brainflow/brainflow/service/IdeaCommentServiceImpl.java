package com.brainflow.brainflow.service;

import com.brainflow.brainflow.dto.request.CommentRequestDTO;
import com.brainflow.brainflow.dto.response.IdeaCommentResponseDTO;
import com.brainflow.brainflow.entity.Idea;
import com.brainflow.brainflow.entity.IdeaComment;
import com.brainflow.brainflow.entity.User;
import com.brainflow.brainflow.repository.IdeaCommentRepository;
import com.brainflow.brainflow.repository.IdeaRepository;
import com.brainflow.brainflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IdeaCommentServiceImpl implements IdeaCommentService {

    private final IdeaCommentRepository commentRepository;
    private final IdeaRepository ideaRepository;
    private final UserRepository userRepository;

    @Autowired
    public IdeaCommentServiceImpl(IdeaCommentRepository commentRepository, IdeaRepository ideaRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.ideaRepository = ideaRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<IdeaCommentResponseDTO> getCommentsByIdea(Long ideaId) {
        if (!ideaRepository.existsById(ideaId)) {
            throw new RuntimeException("Idea not found with id: " + ideaId);
        }
        List<IdeaComment> comments = commentRepository.findByIdeaIdOrderByCreatedAtAsc(ideaId);
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public IdeaCommentResponseDTO addComment(Long ideaId, CommentRequestDTO request, String userEmail) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found with id: " + ideaId));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        if (request.getParentId() != null) {
            boolean parentExists = commentRepository.existsById(request.getParentId());
            if (!parentExists) {
                throw new RuntimeException("Parent comment not found with id: " + request.getParentId());
            }
        }

        IdeaComment comment = new IdeaComment();
        comment.setContent(request.getContent());
        comment.setIdea(idea);
        comment.setUser(user);
        comment.setParentId(request.getParentId());

        IdeaComment saved = commentRepository.save(comment);
        return convertToDTO(saved);
    }

    private IdeaCommentResponseDTO convertToDTO(IdeaComment comment) {
        return new IdeaCommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getIdea().getId(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getUser().getEmail(),
                comment.getParentId(),
                comment.isResolved()
        );
    }

    @Override
    public IdeaCommentResponseDTO resolveComment(Long commentId, boolean resolved) {
        IdeaComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        comment.setResolved(resolved);
        IdeaComment saved = commentRepository.save(comment);
        return convertToDTO(saved);
    }
}
