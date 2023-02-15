package com.freeefly.webfluxpatterns.sec04.service;

import com.freeefly.webfluxpatterns.sec04.client.ProductClient;
import com.freeefly.webfluxpatterns.sec04.dto.Address;
import com.freeefly.webfluxpatterns.sec04.dto.OrderRequest;
import com.freeefly.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.freeefly.webfluxpatterns.sec04.dto.OrderResponse;
import com.freeefly.webfluxpatterns.sec04.dto.Product;
import com.freeefly.webfluxpatterns.sec04.dto.Status;
import com.freeefly.webfluxpatterns.sec04.util.DebugUtil;
import com.freeefly.webfluxpatterns.sec04.util.OrchestrationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderCancellationService orderCancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> mono) {
        return mono
            .map(OrchestrationRequestContext::new)
            .flatMap(orderFulfillmentService:: placeOrder)
            .doOnNext(this::doOrderPostProcessing)
            .doOnNext(DebugUtil::print)
            .map(this::toOrderResponse)
            ;
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
