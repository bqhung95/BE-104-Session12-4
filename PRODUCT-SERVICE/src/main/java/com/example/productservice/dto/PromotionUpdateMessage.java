package com.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionUpdateMessage implements Serializable {
    private Long productId;
    private String promotionId;
    private String action;
    private long timestamp;
}
