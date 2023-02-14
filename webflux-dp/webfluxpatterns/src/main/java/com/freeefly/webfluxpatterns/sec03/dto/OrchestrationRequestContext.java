package com.freeefly.webfluxpatterns.sec03.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class OrchestrationRequestContext {

    private final UUID orderId = UUID.randomUUID();
    private OrderRequest orderRequest;
    private Integer productPrice;
    private PaymentRequest paymentRequest;
    private PaymentResponse paymentResponse;
    private InventoryRequest inventoryRequest;
    private InventoryResponse inventoryResponse;
    private ShippingRequest shippingRequest;
    private ShippingResponse shippingResponse;

    public OrchestrationRequestContext(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }
}
