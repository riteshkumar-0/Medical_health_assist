package com.medical.controller;

import com.medical.dto.HealthInsightsResponse;
import com.medical.dto.WellnessRecommendationResponse;
import com.medical.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getDashboardSummary() {
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }

    @GetMapping("/insights")
    public ResponseEntity<HealthInsightsResponse> getHealthInsights() {
        return ResponseEntity.ok(dashboardService.generateHealthInsights());
    }

    @GetMapping("/wellness/{identifier}")
    public ResponseEntity<WellnessRecommendationResponse> getWellnessRecommendations(
            @PathVariable String identifier) {
        return ResponseEntity.ok(dashboardService.getWellnessRecommendations(identifier));
    }
}
