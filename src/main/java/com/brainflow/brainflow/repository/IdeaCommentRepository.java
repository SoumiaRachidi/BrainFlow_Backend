package com.brainflow.brainflow.repository;

import com.brainflow.brainflow.entity.IdeaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IdeaCommentRepository extends JpaRepository<IdeaComment, Long> {
    List<IdeaComment> findByIdeaIdOrderByCreatedAtAsc(Long ideaId);
}
