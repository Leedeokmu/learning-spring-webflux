package com.freeefly.webfluxpatterns.sec03.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ShippingResponse {
    private UUID orderId;
    private Integer quantity;
    private String expectedDelivery;
    private Address address;
    private Status status;
}
