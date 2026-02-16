package com.medical.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WellnessRecommendationResponse {
    private String productName;
    private String category;
    private String recommendations;
    private String templateUsed;
}
