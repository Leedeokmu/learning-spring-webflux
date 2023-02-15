package com.freeefly.webfluxpatterns.sec04.util;

import com.freeefly.webfluxpatterns.sec04.dto.InventoryRequest;
import com.freeefly.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.freeefly.webfluxpatterns.sec04.dto.PaymentRequest;
import com.freeefly.webfluxpatterns.sec04.dto.ShippingRequest;

public class OrchestrationUtil {
    public static void buildPaymentRequest(OrchestrationRequestContext context) {
        PaymentRequest paymentRequest = PaymentRequest.of(
            context.getOrderRequest().getUserId(),
            context.getProductPrice() * context.getOrderRequest().getQuantity(),
            context.getOrderId()
        );
        context.setPaymentRequest(paymentRequest);
    }
    public static void buildInventoryRequest(OrchestrationRequestContext context) {
        InventoryRequest inventoryRequest = InventoryRequest.of(
            context.getPaymentResponse().getPaymentID(),
            context.getOrderRequest().getProductId(),
            context.getOrderRequest().getQuantity()
        );
        context.setInventoryRequest(inventoryRequest);
    }
    public static void buildShippingRequest(OrchestrationRequestContext context) {
        ShippingRequest shippingRequest = ShippingRequest.of(
            context.getInventoryResponse().getInventoryId(),
            context.getOrderRequest().getUserId(),
            context.getOrderRequest().getQuantity()
        );
        context.setShippingRequest(shippingRequest);
    }





}
