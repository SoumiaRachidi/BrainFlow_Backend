package com.brainflow.brainflow.service;

import com.brainflow.brainflow.entity.IntroductionSlide;
import com.brainflow.brainflow.repository.IntroductionSlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IntroductionSlideServiceImpl implements IntroductionSlideService {

    private final IntroductionSlideRepository introductionSlideRepository;

    @Autowired
    public IntroductionSlideServiceImpl(IntroductionSlideRepository introductionSlideRepository) {
        this.introductionSlideRepository = introductionSlideRepository;
    }

    @Override
    public List<IntroductionSlide> getSlides(Long sessionId) {
        return introductionSlideRepository.findBySessionIdOrderBySlideOrderAsc(sessionId);
    }

    @Override
    @Transactional
    public List<IntroductionSlide> saveSlides(Long sessionId, List<IntroductionSlide> slides) {
        introductionSlideRepository.deleteBySessionId(sessionId);
        if (slides != null) {
            for (int i = 0; i < slides.size(); i++) {
                IntroductionSlide slide = slides.get(i);
                slide.setSessionId(sessionId);
                slide.setSlideOrder(i + 1);
                slide.setId(null); // Force creation
                introductionSlideRepository.save(slide);
            }
        }
        return introductionSlideRepository.findBySessionIdOrderBySlideOrderAsc(sessionId);
    }
}
