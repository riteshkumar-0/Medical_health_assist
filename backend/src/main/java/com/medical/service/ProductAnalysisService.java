package com.medical.service;

import com.medical.dto.ProductAnalysisResponse;
import com.medical.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductAnalysisService {

        private final ChatClient.Builder chatClientBuilder;
        private final PromptLibraryService promptLibraryService;
        private final ProductService productService;

        public ProductAnalysisResponse analyzeProduct(String identifier) {
                log.info("Starting product analysis for: {}", identifier);

                ProductDto product = productService.findProduct(identifier);

                Map<String, String> params = new HashMap<>();
                params.put("product_name", product.getProductName());
                params.put("product_id", product.getProductId());
                params.put("category", product.getCategory());
                params.put("description", product.getDescription());
                params.put("features", product.getKeyFeatures());

                String resolvedPrompt = promptLibraryService.resolveTemplate("product-analysis", params);

                ChatClient chatClient = chatClientBuilder.build();
                String analysis = chatClient.prompt()
                                .user(resolvedPrompt)
                                .call()
                                .content();

                log.info("Product analysis completed for: {}", product.getProductName());

                return ProductAnalysisResponse.builder()
                                .productId(product.getProductId())
                                .productName(product.getProductName())
                                .analysis(analysis)
                                .templateUsed("product-analysis")
                                .build();
        }

        public ProductAnalysisResponse analyzeProductBrief(String identifier) {
                log.info("Starting brief product analysis for: {}", identifier);

                ProductDto product = productService.findProduct(identifier);

                Map<String, String> params = new HashMap<>();
                params.put("product_name", product.getProductName());
                params.put("category", product.getCategory());
                params.put("features", product.getKeyFeatures());

                String resolvedPrompt = promptLibraryService.resolveTemplate("product-analysis-brief", params);

                ChatClient chatClient = chatClientBuilder.build();
                String analysis = chatClient.prompt()
                                .user(resolvedPrompt)
                                .call()
                                .content();

                return ProductAnalysisResponse.builder()
                                .productId(product.getProductId())
                                .productName(product.getProductName())
                                .analysis(analysis)
                                .templateUsed("product-analysis-brief")
                                .build();
        }
}
