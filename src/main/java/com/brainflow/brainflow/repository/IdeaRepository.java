package com.brainflow.brainflow.repository;

import com.brainflow.brainflow.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findBySessionId(Long sessionId);
}
