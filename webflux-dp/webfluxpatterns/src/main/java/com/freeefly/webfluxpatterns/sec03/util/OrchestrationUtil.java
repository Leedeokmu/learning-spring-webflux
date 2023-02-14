package com.freeefly.webfluxpatterns.sec03.util;

import com.freeefly.webfluxpatterns.sec03.dto.InventoryRequest;
import com.freeefly.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import com.freeefly.webfluxpatterns.sec03.dto.PaymentRequest;
import com.freeefly.webfluxpatterns.sec03.dto.ShippingRequest;

public class OrchestrationUtil {
    public static void buildRequestContext(OrchestrationRequestContext context) {
        buildPaymentRequest(context);
        buildInventoryRequest(context);
        buildShippingRequest(context);
    }

    private static void buildPaymentRequest(OrchestrationRequestContext context) {
        PaymentRequest paymentRequest = PaymentRequest.of(
            context.getOrderRequest().getUserId(),
            context.getProductPrice() * context.getOrderRequest().getQuantity(),
            context.getOrderId()
        );
        context.setPaymentRequest(paymentRequest);
    }
    private static void buildInventoryRequest(OrchestrationRequestContext context) {
        InventoryRequest inventoryRequest = InventoryRequest.of(
            context.getOrderId(),
            context.getOrderRequest().getProductId(),
            context.getOrderRequest().getQuantity()
        );
        context.setInventoryRequest(inventoryRequest);
    }
    private static void buildShippingRequest(OrchestrationRequestContext context) {
        ShippingRequest shippingRequest = ShippingRequest.of(
            context.getOrderRequest().getUserId(),
            context.getOrderRequest().getQuantity(),
            context.getOrderId()
        );
        context.setShippingRequest(shippingRequest);
    }





}
