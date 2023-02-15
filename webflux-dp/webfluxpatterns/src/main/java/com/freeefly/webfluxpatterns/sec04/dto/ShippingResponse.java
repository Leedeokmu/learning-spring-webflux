package com.freeefly.webfluxpatterns.sec04.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ShippingResponse {
    private UUID shippingId;
    private Integer quantity;
    private String expectedDelivery;
    private Address address;
    private Status status;
}
