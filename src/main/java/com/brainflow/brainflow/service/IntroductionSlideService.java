package com.brainflow.brainflow.service;

import com.brainflow.brainflow.entity.IntroductionSlide;
import java.util.List;

public interface IntroductionSlideService {
    List<IntroductionSlide> getSlides(Long sessionId);
    List<IntroductionSlide> saveSlides(Long sessionId, List<IntroductionSlide> slides);
}
