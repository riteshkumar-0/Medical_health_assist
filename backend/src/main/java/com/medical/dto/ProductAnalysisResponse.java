package com.medical.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAnalysisResponse {
    private String productId;
    private String productName;
    private String analysis;
    private String templateUsed;
}
