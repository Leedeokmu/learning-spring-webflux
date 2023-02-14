package com.freeefly.webfluxpatterns.sec03.client;

import com.freeefly.webfluxpatterns.sec03.dto.ShippingRequest;
import com.freeefly.webfluxpatterns.sec03.dto.ShippingResponse;
import com.freeefly.webfluxpatterns.sec03.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ShippingClient {
    private final WebClient client;
    private final static String SCHEDULE = "schedule";
    private final static String CANCEL = "cancel";

    public ShippingClient(@Value("${sec03.shipping.service}") String baseUrl) {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<ShippingResponse> schedule(ShippingRequest request) {
        return callShippingService(SCHEDULE, request);
    }

    public Mono<ShippingResponse> cancel(ShippingRequest request) {
        return callShippingService(CANCEL, request);
    }

    private Mono<ShippingResponse> callShippingService(String endPoint, ShippingRequest request) {
        return client.post()
            .uri(endPoint)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(ShippingResponse.class)
            .onErrorReturn(buildErrorResponse(request))
            ;
    }

    private ShippingResponse buildErrorResponse(ShippingRequest request) {
        return ShippingResponse.of(
            request.getOrderId(),
            request.getQuantity(),
            null,
            null,
            Status.FAILED
        );
    }
}
