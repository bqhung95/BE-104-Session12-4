package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String PRODUCT_CACHE_PREFIX = "product:";
    private static final Duration TTL = Duration.ofMinutes(10);

    private final ProductRepository repository;
    private final RedisTemplate<String, Object> redisTemplate;

    public Product getProduct(Long id) {
        String key = PRODUCT_CACHE_PREFIX + id;

        // 1. Cache hit
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            log.info("✅ CACHE HIT for product {}", id);
            return (Product) cached;
        }

        // 2. Cache miss → PostgreSQL
        log.info("❌ CACHE MISS → load PostgreSQL for product {}", id);
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        redisTemplate.opsForValue().set(key, product, TTL);
        return product;
    }
}
