package com.example.productservice.subscriber;

import com.example.productservice.dto.PromotionUpdateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PromotionSubscriber {

    private static final String PRODUCT_CACHE_PREFIX = "product:";
    private static final String PRODUCT_LIST_CACHE = "product:all";

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Được gọi tự động khi có message từ channel 'promotion-updates'.
     * Nhiệm vụ: xóa cache của sản phẩm → lần query sau sẽ đọc lại PostgreSQL (giá mới).
     */
    public void handleMessage(PromotionUpdateMessage message) {
        long latency = System.currentTimeMillis() - message.getTimestamp();

        log.info("🔔 Received promotion update: productId={}, promotionId={}, action={}, latency={}ms",
                message.getProductId(), message.getPromotionId(), message.getAction(), latency);

        // 1. Xóa cache chi tiết sản phẩm
        String productKey = PRODUCT_CACHE_PREFIX + message.getProductId();
        Boolean deleted = redisTemplate.delete(productKey);

        // 2. Xóa cache danh sách sản phẩm
        redisTemplate.delete(PRODUCT_LIST_CACHE);

        log.info("🗑️ Cache invalidated: key={}, existed={}", productKey, deleted);
    }
}
