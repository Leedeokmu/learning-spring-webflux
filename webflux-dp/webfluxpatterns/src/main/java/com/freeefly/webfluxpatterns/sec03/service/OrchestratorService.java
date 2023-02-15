package com.freeefly.webfluxpatterns.sec03.service;

import com.freeefly.webfluxpatterns.sec03.client.ProductClient;
import com.freeefly.webfluxpatterns.sec03.dto.Address;
import com.freeefly.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import com.freeefly.webfluxpatterns.sec03.dto.OrderRequest;
import com.freeefly.webfluxpatterns.sec03.dto.OrderResponse;
import com.freeefly.webfluxpatterns.sec03.dto.Product;
import com.freeefly.webfluxpatterns.sec03.dto.Status;
import com.freeefly.webfluxpatterns.sec03.util.DebugUtil;
import com.freeefly.webfluxpatterns.sec03.util.OrchestrationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final ProductClient productClient;
    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderCancellationService orderCancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> mono) {
        return mono
            .map(OrchestrationRequestContext::new)
            .flatMap(this::getProduct)
            .doOnNext(OrchestrationUtil::buildRequestContext)
            .flatMap(orderFulfillmentService:: placeOrder)
            .doOnNext(this::doOrderPostProcessing)
            .doOnNext(DebugUtil::print)
            .map(this::toOrderResponse)
            ;
    }

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext context) {
        return productClient.getProduct(context.getOrderRequest().getProductId())
            .map(Product::getPrice)
            .doOnNext(context::setProductPrice)
            .map(i -> context);
    }

    private void doOrderPostProcessing(OrchestrationRequestContext context) {
        if (Status.FAILED == context.getStatus()) {
            orderCancellationService.cancelOrder(context);
        }
    }

    private OrderResponse toOrderResponse(OrchestrationRequestContext context) {
        boolean isSuccess = Status.SUCCESS == context.getStatus();
        Address address = isSuccess ? context.getShippingResponse().getAddress() : null;
        String deliveryDate = isSuccess ? context.getShippingResponse().getExpectedDelivery() : null;
        return OrderResponse.of(
            context.getOrderRequest().getUserId(),
            context.getOrderRequest().getProductId(),
            context.getOrderId(),
            context.getStatus(),
            address,
            deliveryDate
        );
    }
}
