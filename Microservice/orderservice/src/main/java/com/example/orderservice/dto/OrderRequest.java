package com.example.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
     private Long userId;

    private Long productId;

    private int quantity;
}
