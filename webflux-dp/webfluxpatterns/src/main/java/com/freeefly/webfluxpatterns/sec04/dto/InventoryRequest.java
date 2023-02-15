package com.freeefly.webfluxpatterns.sec04.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class InventoryRequest {
    private UUID paymentId;
    private Integer productId;
    private Integer quantity;

}
