package com.medical.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthInsightsResponse {
    private String insights;
    private int totalProducts;
    private List<String> categories;
    private String templateUsed;
}
