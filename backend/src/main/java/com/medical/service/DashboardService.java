package com.medical.service;

import com.medical.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

        private final ChatClient.Builder chatClientBuilder;
        private final PromptLibraryService promptLibraryService;
        private final ProductService productService;

        public HealthInsightsResponse generateHealthInsights() {
                log.info("Generating health insights dashboard");

                List<ProductDto> products = productService.getAllProducts();

                String productCatalog = products.stream()
                                .map(p -> String.format(
                                                "Product: %s (ID: %s)\n  Category: %s\n  Description: %s\n  Features: %s",
                                                p.getProductName(), p.getProductId(), p.getCategory(),
                                                p.getDescription(), p.getKeyFeatures()))
                                .collect(Collectors.joining("\n\n"));

                Map<String, String> params = new HashMap<>();
                params.put("product_catalog", productCatalog);

                String resolvedPrompt = promptLibraryService.resolveTemplate("health-insights", params);

                ChatClient chatClient = chatClientBuilder.build();
                String insights = chatClient.prompt()
                                .user(resolvedPrompt)
                                .call()
                                .content();

                List<String> categories = products.stream()
                                .map(ProductDto::getCategory)
                                .distinct()
                                .collect(Collectors.toList());

                return HealthInsightsResponse.builder()
                                .insights(insights)
                                .totalProducts(products.size())
                                .categories(categories)
                                .templateUsed("health-insights")
                                .build();
        }

        public WellnessRecommendationResponse getWellnessRecommendations(String identifier) {
                log.info("Generating wellness recommendations for: {}", identifier);

                ProductDto product = productService.findProduct(identifier);

                Map<String, String> params = new HashMap<>();
                params.put("product_name", product.getProductName());
                params.put("features", product.getKeyFeatures());
                params.put("category", product.getCategory());

                String resolvedPrompt = promptLibraryService.resolveTemplate("wellness-recommendation", params);

                ChatClient chatClient = chatClientBuilder.build();
                String recommendations = chatClient.prompt()
                                .user(resolvedPrompt)
                                .call()
                                .content();

                return WellnessRecommendationResponse.builder()
                                .productName(product.getProductName())
                                .category(product.getCategory())
                                .recommendations(recommendations)
                                .templateUsed("wellness-recommendation")
                                .build();
        }

        public Map<String, Object> getDashboardSummary() {
                List<ProductDto> products = productService.getAllProducts();
                List<String> categories = products.stream()
                                .map(ProductDto::getCategory)
                                .distinct()
                                .collect(Collectors.toList());

                Map<String, Long> categoryCount = products.stream()
                                .collect(Collectors.groupingBy(ProductDto::getCategory, Collectors.counting()));

                Map<String, Object> summary = new HashMap<>();
                summary.put("totalProducts", products.size());
                summary.put("categories", categories);
                summary.put("categoryDistribution", categoryCount);
                summary.put("products", products);
                return summary;
        }
}
