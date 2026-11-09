package com.example.promotionservice.controller;

import com.example.promotionservice.service.PromotionPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionPublisher publisher;

    @PostMapping("/update")
    public ResponseEntity<?> updatePromotion(@RequestBody Map<String, Object> body) {
        Long productId = Long.valueOf(body.get("productId").toString());
        String promotionId = body.get("promotionId").toString();

        publisher.publishPromotionUpdate(productId, promotionId, "UPDATE");
        return ResponseEntity.ok(Map.of(
                "status", "published",
                "productId", productId,
                "promotionId", promotionId
        ));
    }
}
