package com.medical.service;

import com.medical.dto.ProductComparisonRequest;
import com.medical.dto.ProductComparisonResponse;
import com.medical.dto.ProductDto;
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
public class ProductComparisonService {

        private final ChatClient.Builder chatClientBuilder;
        private final PromptLibraryService promptLibraryService;
        private final ProductService productService;

        public ProductComparisonResponse compareProducts(ProductComparisonRequest request) {
                log.info("Comparing products: {} vs {}", request.getProductIdA(), request.getProductIdB());

                ProductDto productA = productService.findProduct(request.getProductIdA());
                ProductDto productB = productService.findProduct(request.getProductIdB());

                String templateKey;
                Map<String, String> params = new HashMap<>();

                if ("quick".equalsIgnoreCase(request.getComparisonType())) {
                        templateKey = "product-comparison-quick";
                        params.put("product_a_name", productA.getProductName());
                        params.put("product_b_name", productB.getProductName());
                        params.put("product_a_features", productA.getKeyFeatures());
                        params.put("product_b_features", productB.getKeyFeatures());
                } else {
                        templateKey = "product-comparison";
                        params.put("product_a_name", productA.getProductName());
                        params.put("product_a_category", productA.getCategory());
                        params.put("product_a_description", productA.getDescription());
                        params.put("product_a_features", productA.getKeyFeatures());
                        params.put("product_b_name", productB.getProductName());
                        params.put("product_b_category", productB.getCategory());
                        params.put("product_b_description", productB.getDescription());
                        params.put("product_b_features", productB.getKeyFeatures());
                }

                String resolvedPrompt = promptLibraryService.resolveTemplate(templateKey, params);

                ChatClient chatClient = chatClientBuilder.build();
                String comparison = chatClient.prompt()
                                .user(resolvedPrompt)
                                .call()
                                .content();

                return ProductComparisonResponse.builder()
                                .productAName(productA.getProductName())
                                .productBName(productB.getProductName())
                                .comparison(comparison)
                                .comparisonType(request.getComparisonType())
                                .templateUsed(templateKey)
                                .build();
        }

        public ProductComparisonResponse compareByCategory(String category) {
                log.info("Comparing all products in category: {}", category);

                List<ProductDto> products = productService.getAllProducts()
                                .stream()
                                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                                .collect(Collectors.toList());

                if (products.isEmpty()) {
                        throw new RuntimeException("No products found in category: " + category);
                }

                String productList = products.stream()
                                .map(p -> String.format("- %s (ID: %s): %s | Features: %s",
                                                p.getProductName(), p.getProductId(), p.getDescription(),
                                                p.getKeyFeatures()))
                                .collect(Collectors.joining("\n"));

                Map<String, String> params = new HashMap<>();
                params.put("category", category);
                params.put("product_list", productList);

                String resolvedPrompt = promptLibraryService.resolveTemplate("category-comparison", params);

                ChatClient chatClient = chatClientBuilder.build();
                String comparison = chatClient.prompt()
                                .user(resolvedPrompt)
                                .call()
                                .content();

                return ProductComparisonResponse.builder()
                                .productAName(category + " Category")
                                .productBName(products.size() + " products")
                                .comparison(comparison)
                                .comparisonType("category")
                                .templateUsed("category-comparison")
                                .build();
        }
}
