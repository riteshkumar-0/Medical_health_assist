package com.medical.service;

import com.medical.dto.ProductDto;
import com.medical.entity.Product;
import com.medical.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(String productId) {
        return productRepository.findById(productId).map(this::toDto);
    }

    public Optional<ProductDto> getProductByName(String productName) {
        return productRepository.findByProductNameIgnoreCase(productName).map(this::toDto);
    }

    public ProductDto findProduct(String identifier) {
        Optional<ProductDto> product = getProductById(identifier);
        if (product.isEmpty()) {
            product = getProductByName(identifier);
        }
        return product.orElseThrow(() -> new RuntimeException("Product not found with ID or Name: " + identifier));
    }

    public ProductDto saveProduct(ProductDto dto) {
        Product product = toEntity(dto);
        Product saved = productRepository.save(product);
        return toDto(saved);
    }

    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .category(product.getCategory())
                .keyFeatures(product.getKeyFeatures())
                .build();
    }

    private Product toEntity(ProductDto dto) {
        return Product.builder()
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .keyFeatures(dto.getKeyFeatures())
                .build();
    }
}
