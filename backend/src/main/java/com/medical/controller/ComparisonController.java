package com.medical.controller;

import com.medical.dto.ProductComparisonRequest;
import com.medical.dto.ProductComparisonResponse;
import com.medical.service.ProductComparisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comparison")
@RequiredArgsConstructor
public class ComparisonController {

    private final ProductComparisonService comparisonService;

    @PostMapping
    public ResponseEntity<ProductComparisonResponse> compareProducts(
            @RequestBody ProductComparisonRequest request) {
        return ResponseEntity.ok(comparisonService.compareProducts(request));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ProductComparisonResponse> compareByCategory(
            @PathVariable String category) {
        return ResponseEntity.ok(comparisonService.compareByCategory(category));
    }
}
