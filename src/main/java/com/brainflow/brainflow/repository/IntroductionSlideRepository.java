package com.brainflow.brainflow.repository;

import com.brainflow.brainflow.entity.IntroductionSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntroductionSlideRepository extends JpaRepository<IntroductionSlide, Long> {
    List<IntroductionSlide> findBySessionIdOrderBySlideOrderAsc(Long sessionId);
    void deleteBySessionId(Long sessionId);
}
