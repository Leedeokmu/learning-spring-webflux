package com.freeefly.webfluxpatterns.sec03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class InventoryResponse {
    private Integer productId;
    private Integer quantity;
    private Integer remainingQuantity;
    private Status status;
}
