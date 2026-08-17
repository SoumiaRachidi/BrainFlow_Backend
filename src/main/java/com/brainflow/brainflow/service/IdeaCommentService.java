package com.brainflow.brainflow.service;

import com.brainflow.brainflow.dto.request.CommentRequestDTO;
import com.brainflow.brainflow.dto.response.IdeaCommentResponseDTO;
import java.util.List;

public interface IdeaCommentService {
    List<IdeaCommentResponseDTO> getCommentsByIdea(Long ideaId);
    IdeaCommentResponseDTO addComment(Long ideaId, CommentRequestDTO request, String userEmail);
    IdeaCommentResponseDTO resolveComment(Long commentId, boolean resolved);
}
