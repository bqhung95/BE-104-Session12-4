package com.example.promotionservice.service;

import com.example.promotionservice.dto.PromotionUpdateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionPublisher {

    public static final String CHANNEL = "promotion-updates";

    private final RedisTemplate<String, Object> redisTemplate;

    public void publishPromotionUpdate(Long productId, String promotionId, String action) {
        PromotionUpdateMessage message = new PromotionUpdateMessage(
                productId, promotionId, action, System.currentTimeMillis()
        );

        Long subscriberCount = redisTemplate.convertAndSend(CHANNEL, message);
        log.info("📢 Published to '{}': productId={}, promotionId={}, action={}, subscribers={}",
                CHANNEL, productId, promotionId, action, subscriberCount);
    }
}
