package com.brainflow.brainflow.controller;

import com.brainflow.brainflow.entity.IntroductionSlide;
import com.brainflow.brainflow.service.IntroductionSlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions/{sessionId}/slides")
public class IntroductionSlideController {

    private final IntroductionSlideService slideService;

    @Autowired
    public IntroductionSlideController(IntroductionSlideService slideService) {
        this.slideService = slideService;
    }

    @GetMapping
    public ResponseEntity<List<IntroductionSlide>> getSlides(@PathVariable Long sessionId) {
        return ResponseEntity.ok(slideService.getSlides(sessionId));
    }

    @PostMapping
    public ResponseEntity<List<IntroductionSlide>> saveSlides(
            @PathVariable Long sessionId,
            @RequestBody List<IntroductionSlide> slides) {
        return ResponseEntity.ok(slideService.saveSlides(sessionId, slides));
    }
}
