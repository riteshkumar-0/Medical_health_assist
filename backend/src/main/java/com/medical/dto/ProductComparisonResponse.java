package com.medical.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductComparisonResponse {
    private String productAName;
    private String productBName;
    private String comparison;
    private String comparisonType;
    private String templateUsed;
}
