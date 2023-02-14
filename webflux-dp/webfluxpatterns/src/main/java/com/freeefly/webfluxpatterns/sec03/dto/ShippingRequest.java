package com.freeefly.webfluxpatterns.sec03.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ShippingRequest {
    private Integer userId;
    private Integer quantity;
    private UUID orderId;

}
