package com.freeefly.webfluxpatterns.sec04.service;

import com.freeefly.webfluxpatterns.sec04.client.ProductClient;
import com.freeefly.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.freeefly.webfluxpatterns.sec04.dto.Product;
import com.freeefly.webfluxpatterns.sec04.dto.Status;
import com.freeefly.webfluxpatterns.sec04.util.OrchestrationUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class OrderFulfillmentService {
    private final ProductClient productClient;
    private final PaymentOrchestrator paymentOrchestrator;
    private final InventoryOrchestrator inventoryOrchestrator;
    private final ShippingOrchestrator shippingOrchestrator;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext context) {
        return getProduct(context)
            .doOnNext(OrchestrationUtil::buildPaymentRequest)
            .flatMap(paymentOrchestrator::create)
            .doOnNext(OrchestrationUtil::buildInventoryRequest)
            .flatMap(inventoryOrchestrator::create)
            .doOnNext(OrchestrationUtil::buildShippingRequest)
            .flatMap(shippingOrchestrator::create)
            .doOnNext(c -> c.setStatus(Status.SUCCESS))
            .doOnError(ex -> context.setStatus(Status.FAILED))
            .onErrorReturn(context)
        ;
    }

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext context) {
        return productClient.getProduct(context.getOrderRequest().getProductId())
            .map(Product::getPrice)
            .doOnNext(context::setProductPrice)
            .map(i -> context);
    }




}
