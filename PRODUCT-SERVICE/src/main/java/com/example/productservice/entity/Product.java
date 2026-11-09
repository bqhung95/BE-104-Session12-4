package com.example.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal originalPrice;
    private BigDecimal salePrice;   // giá khuyến mãi hiện tại
    private String promotionId;
}
