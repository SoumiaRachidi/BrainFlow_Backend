package com.brainflow.brainflow.controller;

import com.brainflow.brainflow.dto.response.DashboardMetricsResponseDTO;
import com.brainflow.brainflow.entity.BrainstormingSession;
import com.brainflow.brainflow.entity.SessionStatus;
import com.brainflow.brainflow.repository.BrainstormingSessionRepository;
import com.brainflow.brainflow.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard/admin")
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final UserRepository userRepository;
    private final BrainstormingSessionRepository sessionRepository;

    public AdminDashboardController(UserRepository userRepository, BrainstormingSessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/metrics")
    public ResponseEntity<DashboardMetricsResponseDTO> getMetrics() {
        long sessionsCount = sessionRepository.count();
        long activeUsersCount = userRepository.count();

        List<BrainstormingSession> sessions = sessionRepository.findAll();
        long completedCount = sessions.stream()
                .filter(s -> s.getStatus() == SessionStatus.COMPLETED)
                .count();

        int validatedRate = 0;
        if (sessionsCount > 0) {
            validatedRate = (int) ((completedCount * 100) / sessionsCount);
        } else {
            validatedRate = 75;
        }

        int openIssuesCount = 3;

        DashboardMetricsResponseDTO metrics = new DashboardMetricsResponseDTO(
                sessionsCount,
                activeUsersCount,
                validatedRate,
                openIssuesCount
        );

        return ResponseEntity.ok(metrics);
    }
}
