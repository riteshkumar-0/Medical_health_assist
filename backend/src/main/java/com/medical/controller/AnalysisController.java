package com.medical.controller;

import com.medical.dto.ProductAnalysisResponse;
import com.medical.service.ProductAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final ProductAnalysisService productAnalysisService;

    @GetMapping("/{identifier}")
    public ResponseEntity<ProductAnalysisResponse> analyzeProduct(
            @PathVariable String identifier) {
        return ResponseEntity.ok(productAnalysisService.analyzeProduct(identifier));
    }

    @GetMapping("/{identifier}/brief")
    public ResponseEntity<ProductAnalysisResponse> analyzeProductBrief(
            @PathVariable String identifier) {
        return ResponseEntity.ok(productAnalysisService.analyzeProductBrief(identifier));
    }
}
